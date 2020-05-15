package ym.nemo233.bookstore.parse.impl

import org.jsoup.Jsoup
import ym.nemo233.bookstore.parse.SiteParser
import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
import ym.nemo233.bookstore.sqlite.BooksInformation
import ym.nemo233.bookstore.sqlite.BooksSite
import java.net.URL

class FqxsSiteParser(val booksSite: BooksSite) : SiteParser {
    companion object {
        private const val DECODE = "UTF-8"
    }

    override fun loadBookcaseClassify(booksSite: BooksSite): List<BookcaseClassifyCache>? {
        val doc = Jsoup.parse(URL(booksSite.rootUrl).openStream(), DECODE, booksSite.rootUrl)
        val body = doc.body()
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
    }

    /**
     * 根据分类,加载内容列表
     */
    override fun loadBooksByClassify(classifyCache: BookcaseClassifyCache): List<BooksInformation> {
        val result = ArrayList<BooksInformation>()
        val doc = Jsoup.parse(URL(classifyCache.url).openStream(), DECODE, classifyCache.url)
        doc.getElementById("hotcontent").select("div[class=item]").forEach { item ->
            val imgTag = item.getElementsByTag("img")
            val img = imgTag.attr("src")
            println("tag:img:$imgTag\n$img")
            val dt = item.getElementsByTag("dt")
            val auth = dt.select("span").text()
            val href = dt.select("a[href]")
            val name = href.text()
            val url = classifyCache.url + href.attr("href")
            val instr = item.getElementsByTag("dd").text()
            result.add(
                BooksInformation(
                    null, name, auth, instr,
                    classifyCache.url + img,
                    classifyCache.id,
                    classifyCache.name,
                    false, 0, 0, url, false
                )
            )
        }
        return result
    }

}