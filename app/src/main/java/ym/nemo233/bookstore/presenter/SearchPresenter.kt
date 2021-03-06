package ym.nemo233.bookstore.presenter

import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.basic.SearchBooksView
import ym.nemo233.bookstore.sqlite.WebSite
import ym.nemo233.framework.mvp.BasePresenter

/**
 * 搜索
 */
class SearchPresenter(view: SearchBooksView) : BasePresenter<SearchBooksView>() {

    init {
        attachView(view)
    }
//
//    fun searchBooks(type: Int, txt: String) {
//        if (type == 0) {
//            val siteParser = SiteParseFactory.loadDefault()
//            search(siteParser, txt)
//        } else {
//            DBHelper.loadBookSite()?.forEach {
//                val siteParser = SiteParseFactory.create(it)
//                search(siteParser, txt)
//            }
//        }
//    }
//
//    private fun search(siteParser: SiteParser, txt: String) {
//        Thread {
//            val result = siteParser.searchBook(txt)
//            mvpView?.onResultBySearch(result)
//        }.start()
//    }

    fun loadSiteList() {
        val data = DBHelper.loadBookSite()?:ArrayList()
        data.add(0, WebSite("历史记录"))
        mvpView?.onResultForLocalData(data)
    }
}