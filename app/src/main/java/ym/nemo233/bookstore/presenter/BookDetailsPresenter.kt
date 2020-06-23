package ym.nemo233.bookstore.presenter

import ym.nemo233.bookstore.basic.BookDetailsView
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.beans.TempBook
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.BooksInformation
import ym.nemo233.bookstore.utils.Te
import ym.nemo233.framework.mvp.BasePresenter
import ym.nemo233.framework.utils.L

class BookDetailsPresenter(view: BookDetailsView) : BasePresenter<BookDetailsView>() {
    private val siteParser by lazy { SiteParseFactory.loadDefault() }
    private var booksInformation: BooksInformation? = null

    init {
        attachView(view)
    }

    fun loadBookDetails(tempBook: TempBook) {
        Thread {
            booksInformation = siteParser.loadBookInformation(tempBook)

            L.d("[log-any]${Te.toString(booksInformation!!)}")
            if (booksInformation != null) {
                mvpView?.onUpdateBookInfo(booksInformation!!)
            } else {
                mvpView?.onLoadError()
            }
        }.start()
    }

    /**
     * 添加到书架
     */
    fun addToBookcase() {
        booksInformation ?: return
        Thread {
            val now = System.currentTimeMillis()
            val result = siteParser.loadChaptersCache(booksInformation!!)
            L.d("[log-缓存耗时] ${System.currentTimeMillis() - now}")
            mvpView?.onAddToBookcase(result)
        }.start()
    }

    fun checkIsAppendBookcase(booksInformation: BooksInformation): Boolean =
        !DBHelper.hasInsertBookcase(booksInformation)
}