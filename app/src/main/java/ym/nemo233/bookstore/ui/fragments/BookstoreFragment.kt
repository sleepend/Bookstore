package ym.nemo233.bookstore.ui.fragments

import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.BookstoreView
import ym.nemo233.bookstore.beans.PopularBookArray
import ym.nemo233.bookstore.presenter.BookstorePresenter
import ym.nemo233.framework.YMMVPFragment

/**
 * 书城
 */
class BookstoreFragment : YMMVPFragment<BookstorePresenter>(), BookstoreView {

    override fun getLayoutId(): Int = R.layout.fragment_bookcase
    override fun createPresenter(): BookstorePresenter? = BookstorePresenter(this)

    override fun initView() {

    }

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadData()
    }

    override fun onShowBooksSiteTitle(booksSiteName: String) {

    }

    override fun onLoadBookstore(data: List<PopularBookArray>) {

    }
}