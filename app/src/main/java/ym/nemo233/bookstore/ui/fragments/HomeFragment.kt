package ym.nemo233.bookstore.ui.fragments

import android.content.Intent
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.BookcaseView
import ym.nemo233.bookstore.presenter.BookcasePresenter
import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.bookstore.ui.activity.ReaderActivity
import ym.nemo233.bookstore.ui.activity.SearchActivity
import ym.nemo233.bookstore.ui.adapter.BookcaseAdapter
import ym.nemo233.framework.YMMVPFragment

/**
 * 主页
 */
class HomeFragment : YMMVPFragment<BookcasePresenter>(), BookcaseView {
    private val adapter by lazy { BookcaseAdapter() }

    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun createPresenter(): BookcasePresenter? = BookcasePresenter(this)

    override fun initView() {
        home_recycler.layoutManager = LinearLayoutManager(activity)
        home_recycler.adapter = adapter
        home_recycler.itemAnimator = DefaultItemAnimator()
        adapter.bindToRecyclerView(home_recycler)
        adapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as BookInformation
            ReaderActivity.skipTo(mContext, item)
        }
    }

    override fun bindEvent() {
        super.bindEvent()
        home_search.setOnClickListener {
            startActivity(Intent(mActivity, SearchActivity::class.java))
        }
    }

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadBookcase()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            mvp?.loadBookcase()
        }
    }

    override fun onLoadBookcase(data: List<BookInformation>) {
        adapter.setNewData(data)
    }
}