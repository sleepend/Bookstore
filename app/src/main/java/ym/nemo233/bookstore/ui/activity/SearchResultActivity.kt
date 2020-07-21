package ym.nemo233.bookstore.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.top_bar_search.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.sqlite.HotBook
import ym.nemo233.bookstore.thread.SearchThread
import ym.nemo233.bookstore.ui.adapter.HotBookAdapter
import ym.nemo233.framework.YMActivity

class SearchResultActivity : YMActivity(), SearchThread.OnResultForSearch {
    private val keywords by lazy { intent.getStringExtra(KEYWORDS) }
    private val adapter by lazy { HotBookAdapter() }

    override fun getLayoutId(): Int = R.layout.activity_search_result

    companion object {
        private const val KEYWORDS = "KEYWORDS"
        fun skipTo(context: Context, keywords: String) {
            val intent = Intent(context, SearchResultActivity::class.java)
            intent.putExtra(KEYWORDS, keywords)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        topbar_search_view.setText(keywords)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter
        recycler.itemAnimator = DefaultItemAnimator()
    }

    override fun bindEvent() {
        super.bindEvent()
        topbar_back.setOnClickListener { finish() }
        topbar_clear.setOnClickListener { topbar_search_view.setText("") }
        topbar_search.setOnClickListener {
            val keywords = topbar_search_view.text.toString()
            if (keywords.isEmpty()) {
                return@setOnClickListener
            }
            search_tips_loading.visibility = View.VISIBLE
            search_tips_fail.visibility = View.VISIBLE
            queryBookByKeywords(keywords)
        }
        adapter.bindToRecyclerView(recycler)
        adapter.setOnItemClickListener { adapter, _, position ->
            val data = adapter.getItem(position) as HotBook
            BookDetailsActivity.skipTo(this@SearchResultActivity, data)
        }
    }

    override fun firstRequest() {
        super.firstRequest()
        if (keywords.isNotEmpty()) {
            queryBookByKeywords(keywords)
        }
    }

    private fun queryBookByKeywords(keywords: String) {
        GlobalScope.launch {
            GlobalScope.async {
                DBHelper.loadWebsites()
            }.await().forEach { webSite ->
                val runnable = SearchThread(webSite).apply {
                    this.keywords = keywords
                    this.onResultForSearch = this@SearchResultActivity
                }
                Thread(runnable).start()
                DBHelper.saveHistoryQuery(keywords)
            }
        }
    }

    override fun onResultForSearch(data: List<HotBook>?) {
        runOnUiThread {
            adapter.setNewData(data)
            search_tips_loading.visibility = View.GONE
            search_tips_fail.visibility = View.GONE
        }
    }
}
