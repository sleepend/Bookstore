package ym.nemo233.bookstore.presenter

import ym.nemo233.bookstore.basic.BookDetailsView
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.bookstore.sqlite.HotBook
import ym.nemo233.framework.mvp.BasePresenter
import ym.nemo233.framework.utils.L

class BookDetailsPresenter(view: BookDetailsView) : BasePresenter<BookDetailsView>() {

    init {
        attachView(view)
    }

    fun loadBookDetails(hotBook: HotBook) {
        Thread {
            val siteParser = SiteParseFactory.loadDefault(hotBook.siteTag)
            L.d("[log-parser] $siteParser")
            val bookInformation = siteParser?.loadBookInformation(hotBook)
            if (bookInformation == null) {
                L.d("[log] 加载网络数据失败")
                mvpView?.onLoadError()
            } else {
                bookInformation.id = DBHelper.loadBookId(bookInformation)
                mvpView?.onUpdateBookInfo(bookInformation)
            }
        }.start()
    }

    /**
     * 添加到书架
     */
    fun addToBookcase(bookInformation: BookInformation) {
        Thread {
            DBHelper.saveBook(bookInformation)
            val book = DBHelper.findBookByNotId(bookInformation)
            if (book == null) {
                mvpView?.appendFailed()
            } else {
                //只缓存目录,不保存所有章节内容
                val siteParser = SiteParseFactory.loadDefault(bookInformation.siteName)
                val chapters = siteParser?.loadAllChapters(book)
                if (chapters.isNullOrEmpty()) {
                    mvpView?.appendFailed()
                } else {
                    DBHelper.saveChapters(chapters)
                    mvpView?.appendSuccess()
                }
            }
        }.start()
    }

}