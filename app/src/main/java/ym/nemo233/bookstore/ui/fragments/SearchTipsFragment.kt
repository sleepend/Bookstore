package ym.nemo233.bookstore.ui.fragments

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_tips.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.sqlite.HistoryQuery
import ym.nemo233.bookstore.ui.activity.SearchResultActivity
import ym.nemo233.bookstore.ui.adapter.SearchTipsAdapter
import ym.nemo233.framework.YMFragment

/**
 * 历史搜索的提示符
 */
class SearchTipsFragment : YMFragment() {
    private val adapter by lazy { SearchTipsAdapter() }

    companion object {
        private const val WEB_SITE = "WEB_SITE"

        fun getInstance(): YMFragment = SearchTipsFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_search_tips

    override fun initView() {
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
        recycler.itemAnimator = DefaultItemAnimator()
    }

    override fun firstRequest() {
        super.firstRequest()
        loadHistory()
    }

    override fun bindEvent() {
        super.bindEvent()
        adapter.setOnItemClickListener { adapter, _, position ->
            val data = adapter.getItem(position) as HistoryQuery
            //执行查询
            if (data.searchKey == "关于") {
                //弹出提示框
            } else {
                //开始执行搜索
                SearchResultActivity.skipTo(mContext, data.searchKey)
            }
        }
    }

    private fun loadHistory() {
        val data = DBHelper.loadSearchKeywords()
        if (data.isEmpty()) {
            data.add(HistoryQuery(-1, "关于", 0L))
        }
        loadResultSuccess(data)
    }

    /**
     * 数据加载成功,显示界面
     */
    private fun loadResultSuccess(data: MutableList<HistoryQuery>) {
        search_tips_loading.visibility = View.GONE
        search_tips_fail.visibility = View.GONE
        adapter.setNewData(data)
    }
}