package ym.nemo233.bookstore.presenter

import android.app.Activity
import ym.nemo233.bookstore.basic.BookstoreView
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.parse.SiteParser
import ym.nemo233.framework.mvp.BasePresenter

class BookstorePresenter(view: BookstoreView) : BasePresenter<BookstoreView>() {

    private lateinit var siteParser: SiteParser

    init {
        attachView(view)
    }

    /**
     * 开始加载数据
     */
    fun loadData(activity: Activity) {
        //加载网络数据
        Thread {
            siteParser = SiteParseFactory.loadDefault()
            var data = siteParser.loadBookcaseClassify()
            if (data == null) {
                val list = DBHelper.loadBookSite()
                if (list == null) {
                    activity.runOnUiThread {
                        mvpView?.onLoadFailed()
                    }
                } else {
                    var state = 0
                    list.forEach {
                        siteParser = SiteParseFactory.create(it)
                        data = siteParser.loadBookcaseClassify()
                        if (data != null) {
                            state = 1
                            activity.runOnUiThread {
                                mvpView?.onLoadClassify(data)
                            }
                            return@forEach
                        }
                    }
                    if (state == 0) {
                        activity.runOnUiThread {
                            mvpView?.onLoadFailed()
                        }
                    }
                }

            } else {
                activity.runOnUiThread {
                    mvpView?.onLoadClassify(data)
                }
            }

        }.start()
    }
}
