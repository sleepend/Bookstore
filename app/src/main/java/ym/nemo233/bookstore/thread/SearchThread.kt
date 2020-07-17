package ym.nemo233.bookstore.thread

import ym.nemo233.bookstore.sqlite.WebSite
import ym.nemo233.framework.utils.L

/**
 * 搜索线程,多站点搜索
 */
class SearchThread(val webSite: WebSite, val keywords: String) : Runnable {

    override fun run() {
        val searchUrl = webSite.url + webSite.searchUrl
        L.v("[log-url]search url: $searchUrl    keywords:$keywords ")
    }
}