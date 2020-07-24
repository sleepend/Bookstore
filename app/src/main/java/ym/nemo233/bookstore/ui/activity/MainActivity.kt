package ym.nemo233.bookstore.ui.activity

import android.content.Intent
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.basic.MainView
import ym.nemo233.bookstore.presenter.MainPresenter
import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.bookstore.ui.adapter.BookcaseAdapter
import ym.nemo233.framework.YMMVPActivity

/**
 * 需要增加引导页 splash
 */
class MainActivity : YMMVPActivity<MainPresenter>(), MainView {
    private val adapter by lazy { BookcaseAdapter() }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun createPresenter(): MainPresenter? = MainPresenter(this)

    override fun initView() {
        super.initView()
        home_recycler.layoutManager = LinearLayoutManager(this)
        home_recycler.adapter = adapter
        home_recycler.itemAnimator = DefaultItemAnimator()
    }

    override fun bindEvent() {
        super.bindEvent()
        about.setOnClickListener {

        }
        search.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
        adapter.bindToRecyclerView(home_recycler)
        adapter.setOnItemClickListener { adapter, _, position ->
            val item = adapter.getItem(position) as BookInformation
            ReaderActivity.skipTo(this@MainActivity, item)
        }
    }

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadLocalBooks()
        DBHelper.initializationConfiguration()
    }

    override fun onResume() {
        super.onResume()
        mvp?.loadLocalBooks()
    }

    override fun onLoadLocalBooks(books: MutableList<BookInformation>) {
        adapter.setNewData(books)
    }

}
