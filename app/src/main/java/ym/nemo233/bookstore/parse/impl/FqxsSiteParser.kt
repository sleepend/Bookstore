package ym.nemo233.bookstore.parse.impl

import org.jsoup.Connection
import org.jsoup.Jsoup
import ym.nemo233.bookstore.parse.SiteParser
import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.bookstore.sqlite.Chapter
import ym.nemo233.bookstore.sqlite.HotBook
import ym.nemo233.bookstore.sqlite.WebSite
import ym.nemo233.framework.utils.L
import java.net.URL

class FqxsSiteParser(private val webSite: WebSite) : SiteParser {
    companion object {
        private const val TAG = "[log-fq]"
    }

    override fun loadBookInformation(hotBook: HotBook): BookInformation? {
        val bookInformation = BookInformation()
        try {
            val doc = Jsoup.parse(
                URL(hotBook.sourceUrl).openStream(),
                webSite.decode,
                hotBook.sourceUrl
            )
            L.i("[log]load book information ${hotBook.sourceUrl}")
            val head = doc.head()
            bookInformation.name = hotBook.name
            bookInformation.auth = head.select("meta[property=og:novel:author]").attr("content")
            bookInformation.instr = head.select("meta[property=og:description]").attr("content")
            bookInformation.imageUrl = head.select("meta[property=og:image]").attr("content")
            bookInformation.className =
                head.select("meta[property=og:novel:category]").attr("content")
            bookInformation.status = head.select("meta[property=og:novel:status]").attr("content")

            bookInformation.sourceUrl = hotBook.sourceUrl
            bookInformation.siteName = webSite.name

            bookInformation.newest =
                head.select("meta[property=og:novel:latest_chapter_name]").attr("content")
            bookInformation.newestUrl =
                head.select("meta[property=og:novel:latest_chapter_url]").attr("content")
            bookInformation.upt = head.select("meta[property=og:novel:update_time]").attr("content")

            val body = doc.body()
            val allChapterUrl = body.select("div[class=chapter_more]").select("a")[0].attr("href")
            bookInformation.allChapterUrl = if (allChapterUrl.startsWith("/")) {
                webSite.url + allChapterUrl
            } else allChapterUrl
            bookInformation.latest = body.select("ul[class=chapter]").select("a").map { li ->
                val href = li.attr("href")
                val tag = li.text()
                val url = if (href.startsWith("/")) {
                    webSite.url + href
                } else href
                Chapter(tag, url)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bookInformation
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
    override fun search(keywords: String): List<HotBook>? {
        val url = webSite.url + webSite.searchUrl
        L.i("[log-search-url] $url")
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
            .data("keyword", keywords)
            .execute().parse()

        val nowStamp = System.currentTimeMillis()
        return doc.select("div[class=bookbox]").map {
            val info = it.select("div[class=bookinfo]")
            val nameTag = info.select("h4[class=bookname]")
            val name = nameTag.text()
            val href = nameTag.select("a").attr("href")
            L.d("[log-search] $href ${nameTag.select("a")}")
            val auth = it.select("div[class=author]").text()
            val updateTag = info.select("div[class=update]").select("a")
            val chapterName = updateTag.text()
//            val chapterUrl = updateTag.attr("href")
            val sourceUrl = if (href.startsWith("http") or href.startsWith("https")) {
                href
            } else {
                webSite.url + href
            }
            HotBook(null, webSite.name, name, auth, "", sourceUrl, chapterName, nowStamp)
        }
    }

    /**
     * hot
     */
    override fun loadHotBooks(): List<HotBook> {
        try {
            val url = webSite.url + webSite.hotUrl
            L.i("[log-hot-url] $url")
            val html = Jsoup.parse(URL(url).openStream(), webSite.decode, url)
            val data = ArrayList<HotBook>()
            val nowStamp = System.currentTimeMillis()
            html.body().select("div[class=bookbox]").forEach {
                val imgTag = it.getElementsByTag("img")
                val imgUrl = imgTag.attr("src")
                val info = it.select("div[class=bookinfo]")
                val nameTag = info.select("a")[0]
                val name = nameTag.select("a[href]").text()
                val href = nameTag.attr("href")
                val auth = it.select("div[class=author]").text()
                val updateTag = info.select("a")[1]
                val chapterName = updateTag.text()
//                val chapterUrl = updateTag.attr("href")
                val sourceUrl = if (href.startsWith("http") or href.startsWith("https")) {
                    href
                } else {
                    webSite.url + href
                }
                data.add(
                    HotBook(
                        null,
                        webSite.name,
                        name,
                        auth,
                        imgUrl,
                        sourceUrl,
                        chapterName,
                        nowStamp
                    )
                )
            }
            return data
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return emptyList()
    }

    /**
     * 加载所有章节
     */
    override fun loadAllChapters(bookInformation: BookInformation): List<Chapter> {
        val html = Jsoup.parse(
            URL(bookInformation.allChapterUrl).openStream(),
            webSite.decode,
            bookInformation.allChapterUrl
        )
        return html.body().select("ul[class=chapter]").select("a").mapIndexed { i, li ->
            val href = li.attr("href")
            val tag = li.text()
            val url = if (href.startsWith("/")) {
                webSite.url + href
            } else href
            Chapter(null, bookInformation.id, i, tag, url, "")
        }
    }

}