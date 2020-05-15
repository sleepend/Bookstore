package ym.nemo233.bookstore.presenter

import ym.nemo233.bookstore.basic.BookstoreView
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.beans.PopularBookArray
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.parse.SiteParser
import ym.nemo233.bookstore.sqlite.WebsiteSource
import ym.nemo233.framework.mvp.BasePresenter

class BookstorePresenter(view: BookstoreView) : BasePresenter<BookstoreView>() {

    private lateinit var siteParser: SiteParser

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


    fun loadWebsites(): List<WebsiteSource> = DBHelper.loadWebsites()

    /**
     * 开始加载数据
     */
    fun loadData() {
        val booksSite = DBHelper.loadDefaultSite() ?: return
        mvpView?.onShowBooksSiteTitle(booksSite.name)
        siteParser = SiteParseFactory.create(booksSite)
        if (booksSite.needLoadBookcaseClassifyCache()) {
            //加载网络数据
            Thread {
                val data = siteParser.loadBookcaseClassify(booksSite)
                booksSite.putClassifyCaches(data)
                //
                booksSite.classifyCaches.forEach {
                    it.books = siteParser.loadBooksByClassify(it)
                }
                mvpView?.onLoadClassify(booksSite.classifyCaches)
            }.start()
        } else {
            Thread {
                booksSite.classifyCaches.forEach {
                    val books = DBHelper.loadBooksByClassify(it)
                    if (books.isEmpty()) {
                        it.books = siteParser.loadBooksByClassify(it)
                    } else {
                        it.books = books
                    }
                }
                mvpView?.onLoadClassify(booksSite.classifyCaches)
            }.start()
        }
    }
}
