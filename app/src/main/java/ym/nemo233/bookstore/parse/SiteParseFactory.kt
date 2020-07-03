package ym.nemo233.bookstore.parse

import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.parse.impl.FqxsSiteParser
import ym.nemo233.bookstore.sqlite.WebSite

object SiteParseFactory {

    private var siteParser: SiteParser? = null

    fun create(webSite: WebSite): SiteParser {
        siteParser = when (webSite.url) {
            "http://www.fqxs.org" -> FqxsSiteParser(webSite)
//            "http://www.fqxsw.cc" -> TomatoSiteParser(booksSite)
            else -> FqxsSiteParser(webSite)
        }
        return siteParser!!
    }

    fun loadDefault(): SiteParser = siteParser ?: create(DBHelper.loadDefaultSite())


}