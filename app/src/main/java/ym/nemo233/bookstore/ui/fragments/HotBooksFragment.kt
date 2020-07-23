package ym.nemo233.bookstore.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_tips.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.HotBook
import ym.nemo233.bookstore.sqlite.WebSite
import ym.nemo233.bookstore.ui.activity.BookDetailsActivity
import ym.nemo233.bookstore.ui.adapter.HotBookAdapter
import ym.nemo233.framework.YMFragment
import ym.nemo233.framework.utils.L

/**
 * 搜索的提示符
 */
class HotBooksFragment : YMFragment() {
    private val webSite by lazy { arguments?.getParcelable(WEB_SITE) as WebSite }
    private val adapter by lazy { HotBookAdapter() }

    companion object {
        private const val WEB_SITE = "WEB_SITE"

        fun getInstance(webSite: WebSite): YMFragment = HotBooksFragment().apply {
            arguments = Bundle().apply {
                putParcelable(WEB_SITE, webSite)
            }
        }

    }

    override fun getLayoutId(): Int = R.layout.fragment_search_tips

    override fun initView() {
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
        recycler.itemAnimator = DefaultItemAnimator()
    }

    override fun bindEvent() {
        super.bindEvent()
        adapter.bindToRecyclerView(recycler)
        adapter.setOnItemClickListener { adapter, _, position ->
            val data = adapter.getItem(position) as HotBook
            BookDetailsActivity.skipTo(mContext, data)
        }
    }

    override fun firstRequest() {
        super.firstRequest()
        //根据平台,获得推荐列表
        L.d("[log-]请求获取数据")
        loadHotBySite(webSite)
    }

    //根据站点,获得推荐
    private fun loadHotBySite(webSite: WebSite) {
        Thread {
            val cache = DBHelper.loadLocalHotBooks(webSite)
            val now = System.currentTimeMillis()
            if (cache == null || cache.isEmpty() || now - cache.first().stamp > 60 * 60 * 1000) {
                //1小时只请求一次
                val siteParser = SiteParseFactory.create(webSite)
                val books = siteParser.loadHotBooks()
                updateHotBooks(books)
                //缓存列表
                DBHelper.saveHotBooks(webSite, books)
            } else {
                SiteParseFactory.create(webSite)//只创建不执行,缓存当前解析器
                updateHotBooks(cache)
            }
        }.start()
    }

    private fun updateHotBooks(data: List<HotBook>) {
        mActivity.runOnUiThread {
            adapter.setNewData(data)
            search_tips_loading?.visibility = View.GONE
            search_tips_fail?.visibility = View.GONE
        }
    }
}