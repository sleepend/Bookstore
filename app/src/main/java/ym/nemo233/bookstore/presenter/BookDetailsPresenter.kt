package ym.nemo233.bookstore.presenter

import android.app.Activity
import ym.nemo233.bookstore.basic.BookDetailsView
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.BooksInformation
import ym.nemo233.framework.mvp.BasePresenter
import ym.nemo233.framework.utils.L

class BookDetailsPresenter(view: BookDetailsView) : BasePresenter<BookDetailsView>() {
    private val siteParser by lazy { SiteParseFactory.loadDefault() }

    init {
        attachView(view)
    }

    private var isLoadedChapter = false

    fun loadBookDetails(booksInformation: BooksInformation) {
        Thread {
            val chapters = siteParser.loadNewestChapter(booksInformation, 15)
            isLoadedChapter = chapters.isNotEmpty()
            L.d("[log-size] chapter ${chapters.size}")
            mvpView?.onUpdateBookInfo(booksInformation)
            mvpView?.onLoadBookChapters(chapters)
        }.start()
    }

    /**
     * 添加到书架
     */
    fun addToBookcase(activity: Activity, booksInformation: BooksInformation) {
        if (!isLoadedChapter) return
        Thread {
            val now = System.currentTimeMillis()
            val result = siteParser.cacheChapters(booksInformation)
            L.d("[log-缓存耗时] ${System.currentTimeMillis() - now}")
            activity.runOnUiThread {
                mvpView?.onAddToBookcase(result)
            }
        }.start()
    }

    fun checkIsAppendBookcase(booksInformation: BooksInformation): Boolean =
        !DBHelper.hasInsertBookcase(booksInformation)
}