package ym.nemo233.bookstore.ui.fragments

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_bookstore.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.BookstoreView
import ym.nemo233.bookstore.basic.toast
import ym.nemo233.bookstore.beans.PopularBookArray
import ym.nemo233.bookstore.presenter.BookstorePresenter
import ym.nemo233.bookstore.ui.activity.BookDetailsActivity
import ym.nemo233.bookstore.ui.adapter.BookstoreAdapter
import ym.nemo233.framework.YMMVPFragment
import ym.nemo233.framework.utils.L

/**
 * 书栈
 */
class TreasureFragment : YMMVPFragment<BookstorePresenter>(), BookstoreView {
    private val adapter by lazy { BookstoreAdapter() }

    override fun getLayoutId(): Int = R.layout.fragment_bookstore
    override fun createPresenter(): BookstorePresenter? = BookstorePresenter(this)

    override fun initView() {
        L.e("[llll] create view ${this::class.java.simpleName}")
        bs_recycler.layoutManager = LinearLayoutManager(mContext)
        bs_recycler.adapter = adapter
        bs_recycler.itemAnimator = DefaultItemAnimator()
        adapter.setOnItemChildClickListener { adapter, view, position ->
            val data = adapter.data[position] as PopularBookArray
            when (view.id) {
                R.id.item_layout_1 -> BookDetailsActivity.skipTo(mContext, data.data[0])
                R.id.item_layout_2 -> BookDetailsActivity.skipTo(mContext, data.data[1])
                R.id.item_layout_3 -> BookDetailsActivity.skipTo(mContext, data.data[2])
                R.id.item_layout_4 -> BookDetailsActivity.skipTo(mContext, data.data[3])
                R.id.item_bs_more -> {

                }
            }
        }
    }

    override fun bindEvent() {
        super.bindEvent()
        bs_classify.setOnClickListener {
            //打开分类检索
        }
        bs_search_txt.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val imm =
                    mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
        bs_search.setOnClickListener {
            val keyword = bs_search_txt.text.toString()
            toast(keyword)
            hideSortKeyboard(bs_search_txt)
        }
    }

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadBookstore()
    }


    override fun onLoadBookstore(data: List<PopularBookArray>) {
        adapter.setNewData(data)
    }

    override fun onShowBooksSiteTitle(booksSiteName: String) {

    }
}