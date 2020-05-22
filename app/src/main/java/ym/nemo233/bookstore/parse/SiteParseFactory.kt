package ym.nemo233.bookstore.parse

import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.parse.impl.FqxsSiteParser
import ym.nemo233.bookstore.parse.impl.TomatoSiteParser
import ym.nemo233.bookstore.sqlite.BooksSite

object SiteParseFactory {

    private var siteParser: SiteParser? = null

    fun create(booksSite: BooksSite): SiteParser {
        siteParser = when (booksSite.rootUrl) {
            "http://www.fqxs.org" -> FqxsSiteParser(booksSite)
            "http://www.fqxsw.cc" -> TomatoSiteParser(booksSite)
            else -> FqxsSiteParser(booksSite)
        }
        return siteParser!!
    }

    fun loadDefault(): SiteParser = siteParser ?: create(DBHelper.loadDefaultSite())


}