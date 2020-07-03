package ym.nemo233.bookstore.presenter

import ym.nemo233.bookstore.basic.BookDetailsView
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.framework.mvp.BasePresenter
import ym.nemo233.framework.utils.L

class BookDetailsPresenter(view: BookDetailsView) : BasePresenter<BookDetailsView>() {
    private val siteParser by lazy { SiteParseFactory.loadDefault() }
    private lateinit var bookInformation: BookInformation

    init {
        attachView(view)
    }

    fun loadBookDetails(bookInformation: BookInformation) {
        this.bookInformation = bookInformation
        Thread {
            siteParser.loadBookInformation(bookInformation)
            mvpView?.onUpdateBookInfo(bookInformation)
        }.start()
    }

    /**
     * 添加到书架
     */
    fun addToBookcase() {
        if (bookInformation.id != null) return
        Thread {
            val now = System.currentTimeMillis()
            val temp = DBHelper.insertBook(bookInformation)
            if (temp == null) {
                mvpView?.appendFailed()
                return@Thread
            } else {
                bookInformation = temp
            }
            val result = siteParser.loadChaptersCache(bookInformation)
            L.d("[log-缓存耗时] ${System.currentTimeMillis() - now}")
            mvpView?.onAddToBookcase(result)
        }.start()
    }

    fun checkIsAppendBookcase(bookInformation: BookInformation): Boolean =
        !DBHelper.hasInsertBookcase(bookInformation)
}