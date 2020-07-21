package ym.nemo233.bookstore.thread

import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.HotBook
import ym.nemo233.bookstore.sqlite.WebSite

/**
 * 搜索线程,多站点搜索
 */
class SearchThread(webSite: WebSite) : Runnable {
    var onResultForSearch: OnResultForSearch? = null
    var keywords = ""
    private val siteParser by lazy { SiteParseFactory.create(webSite) }

    override fun run() {
        val data = siteParser.search(keywords)
        onResultForSearch?.onResultForSearch(data)
    }


    interface OnResultForSearch {
        fun onResultForSearch(data: List<HotBook>?)
    }
}