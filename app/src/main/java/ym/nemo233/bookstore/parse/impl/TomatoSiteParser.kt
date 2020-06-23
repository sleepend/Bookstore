//package ym.nemo233.bookstore.parse.impl
//
//import org.jsoup.Jsoup
//import ym.nemo233.bookstore.basic.DBHelper
//import ym.nemo233.bookstore.basic.MyApp
//import ym.nemo233.bookstore.parse.SiteParser
//import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
//import ym.nemo233.bookstore.sqlite.BooksInformation
//import ym.nemo233.bookstore.sqlite.BooksSite
//import ym.nemo233.bookstore.sqlite.Chapter
//import ym.nemo233.framework.utils.L
//import java.net.URL
//
//class TomatoSiteParser(private val booksSite: BooksSite) : SiteParser {
//    companion object {
//        private const val DECODE = "GBK"
//        private const val TAG = "[log-fq]"
//    }
//
//    init {
//        booksSite.__setDaoSession(MyApp.instance().daoSession)
//    }
//
//    override fun loadBookcaseClassify(): List<BookcaseClassifyCache>? {
//        try {
//            L.v(TAG, "url = ${booksSite.rootUrl}")
//            val millis = System.currentTimeMillis()
//            val doc = Jsoup.parse(URL(booksSite.rootUrl).openStream(), DECODE, booksSite.rootUrl)
//            val body = doc.body()
//            booksSite.delayMill = (System.currentTimeMillis() - millis).toInt()
//            booksSite.update()
//            return body.getElementsByTag("nav").select("li").take(6).map {
//                val li = it.select("a[href]")
//                BookcaseClassifyCache(
//                    null,
//                    booksSite.id,
//                    li.text(),
//                    booksSite.rootUrl + li.attr("href")
//                )
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return null
//    }
//
//    /**
//     * 根据分类,加载内容列表
//     */
//    override fun loadBooksByClassify(classifyCache: BookcaseClassifyCache): List<BooksInformation> {
//        val result = ArrayList<BooksInformation>()
//        try {
//            L.v(TAG, "url = ${classifyCache.url}")
//            val doc = Jsoup.parse(URL(classifyCache.url).openStream(), DECODE, classifyCache.url)
//            doc.select("li[class=list-group-item]").forEach { item ->
//                val auth = item.select("small[class=text-muted]").text().split(" ")[1]
//                val href = item.select("a[href]")
//                val name = href.text()
//                val url = href.attr("href").let {
//                    if (booksSite.rootUrl.endsWith("/") && it.startsWith("/")) {
//                        it.replaceFirst("/", "")
//                    }
//                    it
//                }
//                val detailsDoc = Jsoup.parse(URL(url).openStream(), DECODE, url).body()
//                val instr = detailsDoc.getElementById("bookIntro").text()
//                val imgUrl =
//                    detailsDoc.select("img[class=img-thumbnail]").select("img[src=$.jpg]")
//                        .attr("src")
//                result.add(
//                    BooksInformation(
//                        null,
//                        name,
//                        auth,
//                        instr,
//                        imgUrl,
//                        classifyCache.id,
//                        classifyCache.name,
//                        "",
//                        url,
//                        booksSite.rootUrl,
//                        "",
//                        ""
//                    )
//                )
//            }
//            classifyCache.books = result
//            return result
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return result
//    }
//
//    /**
//     * 最新N章
//     */
//    override fun loadNewestChapter(
//        booksInformation: BooksInformation,
//        chapterSize: Int
//    ): ArrayList<Chapter> {
//        val data = ArrayList<Chapter>()
//        try {
//            val doc = Jsoup.parse(
//                URL(booksInformation.sourceUrl).openStream(),
//                DECODE,
//                booksInformation.sourceUrl
//            )
//            val body = doc.body()
//            val tags = body.select("p[class=booktag]")
//            booksInformation.contentSize = tags[3].text()
//            booksInformation.status = tags.last().text()
//            booksInformation.upt = body.select("p[class=visible-xs]").text()
//
//            //最新目录
//            body.select("dd[class=col-md-4]").forEach { dd ->
//                val href = dd.select("a[href]")
//                val tag = href.text()
//                val url = href.attr("href")
//                if (tag.split(" ").size > 1) {
//                    if (url.startsWith("http")) {
//                        data.add(
//                            Chapter(
//                                null,
//                                booksInformation._id,
//                                booksInformation.auth,
//                                tag,
//                                url,
//                                ""
//                            )
//                        )
//                    } else {
//                        data.add(
//                            Chapter(
//                                null,
//                                booksInformation._id,
//                                booksInformation.auth,
//                                tag,
//                                booksSite.rootUrl + url,
//                                ""
//                            )
//                        )
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return data
//    }
//
//    /**
//     * 加载详情与目录
//     */
//    override fun loadChaptersCache(booksInformation: BooksInformation): Boolean {
//        try {
//            val doc = Jsoup.parse(
//                URL(booksInformation.sourceUrl).openStream(),
//                DECODE,
//                booksInformation.sourceUrl
//            )
//            val body = doc.body()
//            val data = ArrayList<Chapter>()
//
//            body.select("dd[class=col-md-3]").forEachIndexed { index, dd ->
//                val href = dd.select("a[href]")
//                val tag = href.text()
//                val url = href.attr("href")
//                if (tag.split(" ").size > 1) {
//                    val item = if (url.startsWith("http")) {
//                        Chapter(
//                            null,
//                            booksInformation._id,
//                            "$index",
//                            tag,
//                            url,
//                            ""
//                        )
//                    } else {
//                        Chapter(
//                            null,
//                            booksInformation._id,
//                            "$index",
//                            tag,
//                            booksSite.rootUrl + url,
//                            ""
//                        )
//                    }
//                    data.add(item)
//                    if (data.size >= 50) {
//                        DBHelper.insertChapters(data)
//                        data.clear()
//                    }
//                }
//                DBHelper.insertChapters(data)
//            }
//            return true
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return false
//    }
//
//    override fun loadChapter(chapter: Chapter): Chapter {
//        val body = Jsoup.parse(URL(chapter.url).openStream(), DECODE, chapter.url).body()
//        val content =
//            body.getElementById("htmlContent").html().replace("<br>", "").replace("&nbsp;", "")
//                .trimEnd()
//        //载入下一页 //linkNext
////        val more = body.getElementById("linkNext")
//        chapter.content = content
//        return chapter
//    }
//
//    /**
//     * 搜索
//     */
//    override fun searchBook(bookName: String): List<BooksInformation>? {
//        val result = ArrayList<BooksInformation>()
//
//        return result
//    }
//}