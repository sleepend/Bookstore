package ym.nemo233.bookstore.ui.activity

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.SearchBooksView
import ym.nemo233.bookstore.presenter.SearchPresenter
import ym.nemo233.bookstore.sqlite.BooksInformation
import ym.nemo233.bookstore.ui.adapter.SearchResultAdapter
import ym.nemo233.bookstore.widget.pop.TypePopWindow
import ym.nemo233.framework.YMMVPActivity

/**
 * 搜索
 */
class SearchActivity : YMMVPActivity<SearchPresenter>(), SearchBooksView {
    private val adapter by lazy { SearchResultAdapter() }

    private val popWindow by lazy {
        TypePopWindow(this).apply {
            callback = object : TypePopWindow.YMPopCallback {
                override fun select(type: Int) {
                    search_type.tag = type
                    search_type.text = if (type == 0) "默认" else "全站"
                }
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_search
    override fun createPresenter(): SearchPresenter? = SearchPresenter(this)

    override fun initView() {
        search_recycler.layoutManager = LinearLayoutManager(this)
        search_recycler.adapter = adapter
        search_recycler.itemAnimator = DefaultItemAnimator()
    }

    override fun bindEvent() {
        super.bindEvent()
        backspace.setOnClickListener {
            finish()
        }
        search_btn.setOnClickListener {
            val txt = search_txt.text.toString()
            if (txt.length < 2) {
                return@setOnClickListener
            }
            adapter.setNewData(null)
            val type = search_type.tag?.toString()?.toInt() ?: 0
            mvp?.searchBooks(type, txt)
        }
        search_type.setOnClickListener {
            if (!popWindow.isShowing) {
                popWindow.showAsDropDown(it)
            }
        }
        adapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as BooksInformation
            BookDetailsActivity.skipTo(this@SearchActivity, item)
        }
    }

    override fun onResultBySearch(result: List<BooksInformation>?) {
        runOnUiThread {
            result?.let { adapter.addData(it) }
        }
    }

}