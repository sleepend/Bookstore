package ym.nemo233.bookstore.parse.impl

import android.os.Looper
import org.jsoup.Connection
import org.jsoup.Jsoup
import ym.nemo233.bookstore.parse.SiteParser
import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.bookstore.sqlite.Chapter
import ym.nemo233.bookstore.sqlite.WebSite
import ym.nemo233.framework.utils.L
import java.net.URL

class FqxsSiteParser(private val webSite: WebSite) : SiteParser {
    companion object {
        private const val TAG = "[log-fq]"
    }

    override fun getWebSiteId(): Long = webSite.id

    /**
     * 加载导航大分类
     */
    override fun loadBookcaseClassify(): List<WebSite>? {
        try {
            L.v(TAG, "url = ${webSite.url}")
            val millis = System.currentTimeMillis()
            val doc = Jsoup.parse(URL(webSite.url).openStream(), webSite.decode, webSite.url)
            val body = doc.body()
            webSite.delayMill = (System.currentTimeMillis() - millis).toInt()
            val lst = body.select("div[class=nav]").select("li")
            return lst.subList(1, lst.size - 3).map {
                val li = it.select("a[href]")
                val href = li.attr("href")
                val url = if (href.startsWith("/")) {
                    webSite.url + href
                } else href
                WebSite(
                    null,
                    li.text(),
                    url,
                    webSite.decode,
                    webSite.delayMill,
                    0,
                    "",
                    webSite.id
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
    override fun loadBooksByClassify(webSite: WebSite): List<BookInformation>? {
        L.v(TAG, "url = ${webSite.url}")
        val doc = Jsoup.parse(URL(webSite.url).openStream(), webSite.decode, webSite.url)
        val lst = doc.getElementById("hotcontent").select("div[class=item]")
        return lst.map { item ->
            val imgTag = item.getElementsByTag("img")
            val img = imgTag.attr("src")
            val dt = item.getElementsByTag("dt")
            val auth = dt.select("span").text()
            val href = dt.select("a[href]")
            val name = href.text()
            val hrefUrl = href.attr("href")

            val url = if (hrefUrl.startsWith("/")) {
                this.webSite.url + hrefUrl
            } else hrefUrl

            val imgUrl = if (img.startsWith("/")) {
                this.webSite.url + img
            } else img
            BookInformation(name, auth, imgUrl, url)
        }
    }

    override fun loadBookInformation(bookInformation: BookInformation) {
        try {
            val doc = Jsoup.parse(
                URL(bookInformation.sourceUrl).openStream(),
                webSite.decode,
                bookInformation.sourceUrl
            )
            val head = doc.head()
            bookInformation.auth = head.select("meta[property=og:novel:author]").attr("content")
            bookInformation.instr = head.select("meta[property=og:description]").attr("content")
            bookInformation.imageUrl = head.select("meta[property=og:image]").attr("content")
            bookInformation.className =
                head.select("meta[property=og:novel:category]").attr("content")
            bookInformation.status = head.select("meta[property=og:novel:status]").attr("content")
            bookInformation.siteName = webSite.name
            bookInformation.newest =
                head.select("meta[property=og:novel:latest_chapter_name]").attr("content")
            bookInformation.upt = head.select("meta[property=og:novel:update_time]").attr("content")

            val body = doc.body()
            bookInformation.latest = body.getElementById("list").select("dd").take(15).map { dd ->
                val href = dd.select("a[href]")
                val hrefUrl = href.attr("href")
                val url = if (hrefUrl.startsWith("/")) {
                    webSite.url + hrefUrl
                } else hrefUrl
                Chapter(dd.text(), url)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 加载缓存目录
     * 耗时操作,需要开启异步任务
     */
    override fun loadChaptersCache(bookInformation: BookInformation): Boolean {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            throw Exception("cannot run on the main thread.")
        }
        try {
            val doc = Jsoup.parse(
                URL(bookInformation.sourceUrl).openStream(),
                webSite.decode,
                bookInformation.sourceUrl
            )
            val body = doc.body()
            val lst = body.getElementById("list").select("dd")
            val data = lst.takeLast(lst.size - 15).mapIndexed { index, dd ->
                val href = dd.select("a[href]")
                val name = href.text()
                val hrefUrl = href.attr("href")
                val url = if (hrefUrl.startsWith("/")) {
                    webSite.url + hrefUrl
                } else hrefUrl
                Chapter(null, bookInformation.id, index, name, url, null)
            }
            return data.isNotEmpty()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    override fun loadChapter(chapter: Chapter): Chapter {
        val body = Jsoup.parse(URL(chapter.url).openStream(), webSite.decode, chapter.url).body()
        val content =
            body.getElementById("content").html().replace("<p>", "").replace("</p>", "").trimEnd()
        chapter.content = content
        return chapter
    }

    /**
     * 搜索
     */
    override fun searchBook(bookName: String): List<BookInformation>? {
        val url = "${webSite.url}/modules/article/search.php"
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
            .postDataCharset(webSite.decode)
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
                val auth = li.select("span[class=s4]").text()
                val hrefUrl = href.attr("href")
                val url = if (hrefUrl.startsWith("/")) {
                    webSite.url + hrefUrl
                } else hrefUrl
                BookInformation(name, auth, "", url)
            }
        }
        return null
    }

}