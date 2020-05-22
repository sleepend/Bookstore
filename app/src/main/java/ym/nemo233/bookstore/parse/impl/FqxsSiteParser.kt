package ym.nemo233.bookstore.parse.impl

import org.jsoup.Connection
import org.jsoup.Jsoup
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.basic.MyApp
import ym.nemo233.bookstore.parse.SiteParser
import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
import ym.nemo233.bookstore.sqlite.BooksInformation
import ym.nemo233.bookstore.sqlite.BooksSite
import ym.nemo233.bookstore.sqlite.Chapter
import ym.nemo233.framework.utils.L
import java.net.URL

class FqxsSiteParser(private val booksSite: BooksSite) : SiteParser {
    companion object {
        private const val DECODE = "UTF-8"
        private const val TAG = "[log-fq]"
    }

    init {
        booksSite.__setDaoSession(MyApp.instance().daoSession)
    }

    /**
     * 加载导航大分类
     */
    override fun loadBookcaseClassify(): List<BookcaseClassifyCache>? {
        try {
            L.v(TAG, "url = ${booksSite.rootUrl}")
            val millis = System.currentTimeMillis()
            val doc = Jsoup.parse(URL(booksSite.rootUrl).openStream(), DECODE, booksSite.rootUrl)
            val body = doc.body()
            booksSite.delayMill = (System.currentTimeMillis() - millis).toInt()
            booksSite.update()
            val lst = body.select("div[class=nav]").select("li")
            return lst.subList(1, lst.size - 3).map {
                val li = it.select("a[href]")
                BookcaseClassifyCache(
                    null,
                    booksSite.id,
                    li.text(),
                    booksSite.rootUrl + li.attr("href")
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 根据分类,加载分类推荐
     */
    override fun loadBooksByClassify(classifyCache: BookcaseClassifyCache): List<BooksInformation> {
        val result = ArrayList<BooksInformation>()
        L.v(TAG, "url = ${classifyCache.url}")
        val doc = Jsoup.parse(URL(classifyCache.url).openStream(), DECODE, classifyCache.url)
        doc.getElementById("hotcontent").select("div[class=item]").forEach { item ->
            val imgTag = item.getElementsByTag("img")
            val img = imgTag.attr("src")
            val dt = item.getElementsByTag("dt")
            val auth = dt.select("span").text()
            val href = dt.select("a[href]")
            val name = href.text()

            val url = href.attr("href").let {
                if (booksSite.rootUrl.endsWith("/") && it.startsWith("/")) {
                    it.replaceFirst("/", "")
                }
                it
            }

            val instr = item.getElementsByTag("dd").text()
            result.add(
                BooksInformation(
                    null,
                    name,
                    auth,
                    instr,
                    booksSite.rootUrl + img,
                    classifyCache.id,
                    classifyCache.name,
                    "",
                    booksSite.rootUrl + url,
                    booksSite.rootUrl,
                    "",
                    ""
                )
            )
        }
        classifyCache.books = result
        return result
    }

    /**
     * 加载详情&最新章节
     */
    override fun loadNewestChapter(
        booksInformation: BooksInformation,
        chapterSize: Int
    ): ArrayList<Chapter> {
        val data = ArrayList<Chapter>()
        try {
            val doc = Jsoup.parse(
                URL(booksInformation.sourceUrl).openStream(),
                DECODE,
                booksInformation.sourceUrl
            )
            val body = doc.body()

            val tags = body.getElementById("info").getElementsByTag("p")

            booksInformation.status = tags[1].text().split("：")[1]
            booksInformation.upt = tags[2].text()
            if (booksInformation.isSearchResult) {
                //因为搜索结果无图片,所以需要重新加载
                val imgTag = body.getElementById("fmimg").getElementsByTag("img")
                booksInformation.imageUrl = booksSite.rootUrl + imgTag.attr("src")
                booksInformation.instr =
                    body.getElementById("intro").getElementsByTag("p")[0].text()
                booksInformation.baseUrl = booksSite.rootUrl
                //加载类型
                val classifyTag = body.getElementsByClass("con_top")[0].getElementsByTag("a")[1]
                val classify = loadBookcaseClassify()?.singleOrNull {
                    it.url == booksSite.rootUrl + classifyTag.attr("href")
                }
                classify?.let {
                    booksInformation.classId = it.id
                    booksInformation.className = it.name
                }
            }

            body.getElementById("list").select("dd").take(15).forEach { dd ->
                val href = dd.select("a[href]")
                val tag = href.text()
                val url = href.attr("href")
                if (url.startsWith("http")) {
                    data.add(Chapter(null, booksInformation._id, tag, tag, url, ""))
                } else {
                    data.add(
                        Chapter(
                            null,
                            booksInformation._id,
                            tag,
                            tag,
                            booksSite.rootUrl + url,
                            ""
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }

    /**
     * 缓存目录
     */
    override fun cacheChapters(booksInformation: BooksInformation): Boolean {
        try {
            val doc = Jsoup.parse(
                URL(booksInformation.sourceUrl).openStream(),
                DECODE,
                booksInformation.sourceUrl
            )
            val body = doc.body()
            val data = ArrayList<Chapter>()
            var last = Chapter()
            booksInformation.__setDaoSession(MyApp.instance().daoSession)
            booksInformation.insert()
            L.v(TAG,"[log] biId = ${booksInformation._id}")
            val lst = body.getElementById("list").select("dd")
            lst.takeLast(lst.size - 15).forEachIndexed { index, dd ->
                val href = dd.select("a[href]")
                val name = href.text()
                val url = href.attr("href")
                val item = if (url.startsWith("http")) {
                    Chapter(null, booksInformation._id, "$index", name, url, "")
                } else {
                    Chapter(
                        null,
                        booksInformation._id,
                        "$index",
                        name,
                        booksSite.rootUrl + url,
                        ""
                    )
                }
                data.add(item)
                if (index % 50 == 0) {
                    DBHelper.insertChapters(data)
                    data.clear()
                }
                if (lst.size == index + 1) {
                    last = item
                }
            }// else 无用章节
            if (data.isNotEmpty()) {
                DBHelper.insertChapters(data)
            }
            DBHelper.appendToBookstore(booksInformation, last)
            return data.size > 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun loadChapter(chapter: Chapter): Chapter {
        val body = Jsoup.parse(URL(chapter.url).openStream(), DECODE, chapter.url).body()
        val content =
            body.getElementById("content").html().replace("<p>", "").replace("</p>", "").trimEnd()
        chapter.content = content
        return chapter
    }

    /**
     * 搜索
     */
    override fun searchBook(bookName: String): List<BooksInformation>? {
        val result = ArrayList<BooksInformation>()
        val url = "${booksSite.rootUrl}/modules/article/search.php"
        val headers = HashMap<String, String>()
        headers["Accept"] =
            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
        headers["User-Agent"] =
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3"
        headers["Connection"] = "close"
        headers["Cache-Control"] = "no-cache"
        headers["Accept-Encoding"] = "gzip, deflate, br"
        headers["Accept-Language"] = "zh-CN,zh;q=0.9"
        headers["Cache-Control"] = "max-age=0"
        val doc = Jsoup.connect(url).headers(headers)
            .postDataCharset(DECODE)
            .method(Connection.Method.POST)
            .data("keyword", bookName)
            .execute().parse()

        val body = doc.body().getElementById("main")
        val lis = body.select("li")

        if (lis.size > 1) {
            lis.removeAt(0)//删除标题行
            return lis.map { li ->
                val href = li.select("span[class=s2]").select("a[href]")
                val name = href.text()
                val url = href.attr("href")
                val auth = li.select("span[class=s4]").text()
                BooksInformation(
                    null,
                    name,
                    auth,
                    "",
                    "",
                    0,
                    booksSite.name,
                    "status",
                    booksSite.rootUrl + url,
                    booksSite.rootUrl,
                    "",
                    ""
                )
            }
        }
        return result
    }

}