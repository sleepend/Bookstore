package ym.nemo233.bookstore.parse.impl

import org.jsoup.Jsoup
import ym.nemo233.bookstore.parse.SiteParser
import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
import ym.nemo233.bookstore.sqlite.BooksSite
import java.net.URL

class FqxsSiteParser(val booksSite: BooksSite) : SiteParser {
    override fun loadBookcaseClassify(booksSite: BooksSite): List<BookcaseClassifyCache>? {
        val doc = Jsoup.parse(URL(booksSite.rootUrl).openStream(), "UTF-8", booksSite.rootUrl)
        val body = doc.body()
        val lst = body.select("div[class=nav]").select("li")
        return lst.subList(1, lst.size - 3).map {
            val li = it.select("a[href]")
            BookcaseClassifyCache(null, booksSite.id, li.text(), li.attr("href"))
        }
    }
}