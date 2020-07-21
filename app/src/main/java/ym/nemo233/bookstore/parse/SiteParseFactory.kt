package ym.nemo233.bookstore.parse

import ym.nemo233.bookstore.parse.impl.FqxsSiteParser
import ym.nemo233.bookstore.sqlite.WebSite

object SiteParseFactory {
    private val webSites = HashMap<String, WebSite>()

    fun create(webSite: WebSite): SiteParser {
        webSites[webSite.name] = webSite //缓存记录
        return when (webSite.url) {
            "http://m.fqxs.org" -> FqxsSiteParser(webSite)
//            "http://www.fqxsw.cc" -> TomatoSiteParser(booksSite)
            else -> FqxsSiteParser(webSite)
        }
    }

    fun loadDefault(siteName: String): SiteParser? {
        val webSite = webSites[siteName] ?: return null
        return create(webSite)
    }


}