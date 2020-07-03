package ym.nemo233.bookstore.presenter

import ym.nemo233.bookstore.basic.ReaderView
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.framework.mvp.BasePresenter

class ReaderPresenter(view: ReaderView) : BasePresenter<ReaderView>() {
    private val siteParser by lazy { SiteParseFactory.loadDefault() }

    init {
        attachView(view)
    }

    /**
     * 开始初始化加载
     */
    fun loadBookChapters(book: BookInformation) {

    }

    /**
     * 上一页
     */
    fun move2Pre() {

    }

    /**
     * 下一页
     */
    fun move2Next() {

    }

}