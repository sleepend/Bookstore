package ym.nemo233.bookstore.presenter

import ym.nemo233.bookstore.basic.BookstoreView
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.beans.PopularBookArray
import ym.nemo233.bookstore.sqlite.WebsiteSource
import ym.nemo233.bookstore.utils.ParseUtils
import ym.nemo233.framework.mvp.BasePresenter

class BookstorePresenter(view: BookstoreView) : BasePresenter<BookstoreView>() {

    init {
        attachView(view)
    }

    fun loadBookstore() {
        val data = DBHelper.loadPopularBooks()
        val result = data.keys.map {
            PopularBookArray(it, data[it] ?: ArrayList())
        }
        mvpView?.onLoadBookstore(result)
    }


    fun loadWebsites():List<WebsiteSource> = DBHelper.loadWebsites()

    /**
     * 开始加载数据
     */
    fun loadData() {
        val booksSite = DBHelper.loadDefaultSite()?:return
        mvpView?.onShowBooksSiteTitle(booksSite.name)
        if(booksSite.needLoadBookcaseClassifyCache()){
            //加载网络数据
            val data = ParseUtils.loadBookcaseClassify(booksSite)
        }
    }
}