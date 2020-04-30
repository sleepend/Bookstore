package ym.nemo233.bookstore.utils

import org.jsoup.Jsoup
import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
import ym.nemo233.bookstore.sqlite.BooksSite
import java.net.URL

/**
 * JSoup 解析下载
 */
object ParseUtils {

    fun loadBookcaseClassify(booksSite: BooksSite): List<BookcaseClassifyCache>? {
        val doc = Jsoup.parse(URL(booksSite.rootUrl).openStream(), "GBK", booksSite.rootUrl)
        val body = doc.body()
        booksSite.parseRuleStepList.forEach {

        }
        return null
    }

}
