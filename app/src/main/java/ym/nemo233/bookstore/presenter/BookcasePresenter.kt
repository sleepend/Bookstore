package ym.nemo233.bookstore.presenter

import ym.nemo233.bookstore.basic.BookcaseView
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.framework.mvp.BasePresenter

/**
 * 书栈
 */
class BookcasePresenter(view: BookcaseView) : BasePresenter<BookcaseView>() {
    init {
        attachView(view)
    }


    fun loadBookcase(){
        val data = DBHelper.loadBookcase()
        mvpView?.onLoadBookcase(data)
    }
}