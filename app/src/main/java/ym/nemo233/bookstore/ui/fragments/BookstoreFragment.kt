package ym.nemo233.bookstore.ui.fragments

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_bookcase.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.BookstoreView
import ym.nemo233.bookstore.beans.PopularBookArray
import ym.nemo233.bookstore.presenter.BookstorePresenter
import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
import ym.nemo233.bookstore.ui.activity.BookDetailsActivity
import ym.nemo233.bookstore.ui.adapter.BookstoreAdapter
import ym.nemo233.framework.YMMVPFragment
import ym.nemo233.framework.utils.L

/**
 * 书城
 */
class BookstoreFragment : YMMVPFragment<BookstorePresenter>(), BookstoreView {
    private val adapter by lazy { BookstoreAdapter() }

    override fun getLayoutId(): Int = R.layout.fragment_bookcase
    override fun createPresenter(): BookstorePresenter? = BookstorePresenter(this)

    override fun initView() {
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
        recycler.itemAnimator = DefaultItemAnimator()
        adapter.bindToRecyclerView(recycler)
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

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadData()
    }

    override fun onLoadClassify(data: List<BookcaseClassifyCache>?) {
        L.e("[log-size]${data?.size}")
        data?.forEach {
            L.e("[log-size]${it.name}\t\t${it.url}")
        }
        activity?.runOnUiThread {
            adapter.setNewData(data!!)
        }
    }

    override fun onShowBooksSiteTitle(booksSiteName: String) {

    }

    override fun onLoadBookstore(data: List<PopularBookArray>) {

    }
}