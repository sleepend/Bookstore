package ym.nemo233.bookstore.ui.fragments

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_bookcase.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.BookstoreView
import ym.nemo233.bookstore.basic.toast
import ym.nemo233.bookstore.beans.PopularBookArray
import ym.nemo233.bookstore.presenter.BookstorePresenter
import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
import ym.nemo233.bookstore.ui.activity.BookDetailsActivity
import ym.nemo233.bookstore.ui.adapter.BookstoreAdapter
import ym.nemo233.framework.YMMVPFragment

/**
 * 书城
 */
class BookstoreFragment : YMMVPFragment<BookstorePresenter>(), BookstoreView {
    private val adapter by lazy { BookstoreAdapter(activity!!) }

    override fun getLayoutId(): Int = R.layout.fragment_bookcase
    override fun createPresenter(): BookstorePresenter? = BookstorePresenter(this)

    override fun initView() {
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
        recycler.itemAnimator = DefaultItemAnimator()
        adapter.bindToRecyclerView(recycler)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            val data = adapter.data[position] as BookcaseClassifyCache
            when (view.id) {
                R.id.item_layout_1 -> BookDetailsActivity.skipTo(mContext, data.books[0])
                R.id.item_layout_2 -> BookDetailsActivity.skipTo(mContext, data.books[1])
                R.id.item_layout_3 -> BookDetailsActivity.skipTo(mContext, data.books[2])
                R.id.item_layout_4 -> BookDetailsActivity.skipTo(mContext, data.books[3])
                R.id.item_bs_more -> {
                    //启动分类
                }
            }
        }

    }

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadData(mActivity)
    }

    override fun onLoadFailed() {
        //加载失败
        toast("连接失败")
    }

    override fun onLoadClassify(data: List<BookcaseClassifyCache>?) {
        adapter.setNewData(data)
    }

    override fun onLoadBookstore(data: List<PopularBookArray>) {

    }
}