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
            val cache = DBHelper.loadLocalCacheBook(hotBook)
            if (cache == null) {//未添加到书架
                val bookInformation = siteParser?.loadBookInformation(hotBook)
                if (bookInformation == null) {
                    L.d("[log] 加载网络数据失败")
                    mvpView?.onLoadError()
                } else {
                    mvpView?.onUpdateBookInfo(bookInformation)
                }
            } else {
                //加载最新15节&加载失败时,则加载网页数据
                cache.latest = DBHelper.loadLocalChapterByBook(cache)
                if (cache.latest == null || cache.latest.isEmpty()) {
                    val bookInformation = siteParser?.loadBookInformation(hotBook)
                    if (bookInformation == null) {
                        L.d("[log] 本地基本数据,章节数据加载失败")
                        mvpView?.onLoadError()
                    } else {
                        mvpView?.onUpdateBookInfo(bookInformation)
                    }
                } else {
                    mvpView?.onUpdateBookInfo(cache)
                }
            }
        }.start()
    }

    /**
     * 添加到书架
     * 1594888873,1594968298,1595216849
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