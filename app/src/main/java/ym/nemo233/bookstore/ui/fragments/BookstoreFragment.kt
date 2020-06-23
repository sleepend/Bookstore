package ym.nemo233.bookstore.ui.fragments

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_bookcase.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.BookstoreView
import ym.nemo233.bookstore.basic.toast
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
        bookcase_swipe.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_red_light,
            android.R.color.holo_orange_light
        )
    }


    override fun bindEvent() {
        super.bindEvent()
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
        bookcase_swipe.setOnRefreshListener {
            mvp?.loadData(mActivity)
        }
    }

    override fun firstRequest() {
        super.firstRequest()
        bookcase_swipe.post {
            bookcase_swipe.isRefreshing = true
            mvp?.loadData(mActivity)
        }
    }

    override fun onLoadFailed() {
        //加载失败
        toast("连接失败")
        bookcase_swipe.isRefreshing = false
    }

    override fun onLoadClassify(data: List<BookcaseClassifyCache>?) {
        adapter.setNewData(data)
        bookcase_swipe.isRefreshing = false
    }


}