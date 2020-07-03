package ym.nemo233.bookstore.presenter

import android.app.Activity
import ym.nemo233.bookstore.basic.BookstoreView
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.basic.Share
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.framework.mvp.BasePresenter

class BookstorePresenter(view: BookstoreView) : BasePresenter<BookstoreView>() {
    private var webSiteUpdateTime by Share(Share.WEB_SITE_BEFORE_UPDATE_TIME, 0L)
    private var siteParser = SiteParseFactory.loadDefault()

    init {
        attachView(view)
    }

    /**
     * 开始加载数据
     */
    fun loadData(activity: Activity) {
        //加载网络数据
        Thread {
            val now = System.currentTimeMillis()
            if (now - webSiteUpdateTime > 7 * 24 * 60 * 60 * 1000) {
                //已过7天,可更新书城内容
                updateWebsiteContent()
                webSiteUpdateTime = now
            } else {
                val webSiteCache = DBHelper.loadWebsites(siteParser.getWebSiteId())
                if (webSiteCache == null) {//无分类及分类推荐
                    updateWebsiteContent()
                    webSiteUpdateTime = now
                } else {
                    //有分类,且在7天内有更新,加载缓存
                    mvpView?.onLoadClassify(webSiteCache)
                }
            }
        }.start()
    }

    /**
     * 获取书城分类+分类推荐
     */
    private fun updateWebsiteContent() {
        val data = siteParser.loadBookcaseClassify()
        mvpView?.onLoadClassify(data)
    }
}
