package ym.nemo233.bookstore.ui.fragments

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_bookstore.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.toast
import ym.nemo233.bookstore.presenter.BookstorePresenter
import ym.nemo233.bookstore.ui.adapter.BookstoreAdapter
import ym.nemo233.framework.YMFragment
import ym.nemo233.framework.utils.L

/**
 * 书栈
 */
class TreasureFragment : YMFragment() {
    private val adapter by lazy { BookstoreAdapter() }

    override fun getLayoutId(): Int = R.layout.fragment_bookstore

    override fun initView() {
        L.e("[llll] create view ${this::class.java.simpleName}")
        bs_recycler.layoutManager = LinearLayoutManager(mContext)
        bs_recycler.adapter = adapter
        bs_recycler.itemAnimator = DefaultItemAnimator()
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

}