package ym.nemo233.bookstore.presenter

import android.util.Log
import ym.nemo233.bookstore.basic.BookstoreView
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.basic.Share
import ym.nemo233.bookstore.beans.PopularBookArray
import ym.nemo233.bookstore.sqlite.WebsiteSource
import ym.nemo233.framework.mvp.BasePresenter

class BookstorePresenter(view: BookstoreView) : BasePresenter<BookstoreView>() {
    private var baseUrl by Share(Share.DEFAULT_BOOKSTORE_URL, "http://www.fqxs.org")//默认为番茄小说网

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
}