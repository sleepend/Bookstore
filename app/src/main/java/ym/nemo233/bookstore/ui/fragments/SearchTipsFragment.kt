package ym.nemo233.bookstore.ui.fragments

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_tips.*
import org.jsoup.Jsoup
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.HistoryQuery
import ym.nemo233.bookstore.sqlite.WebSite
import ym.nemo233.bookstore.ui.adapter.SearchTipsAdapter
import ym.nemo233.framework.YMFragment
import java.net.URL

/**
 * 搜索的提示符
 */
class SearchTipsFragment : YMFragment() {
    private val webSite by lazy { arguments?.getParcelable(WEB_SITE) as WebSite }
    private val adapter by lazy { SearchTipsAdapter() }

    companion object {
        private const val WEB_SITE = "WEB_SITE"

        fun getInstance(webSite: WebSite): YMFragment {
            val fragment = SearchTipsFragment()
            fragment.arguments?.putParcelable(WEB_SITE, webSite)
            return fragment
        }

    }

    override fun getLayoutId(): Int = R.layout.fragment_search_tips

    override fun initView() {
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
        recycler.itemAnimator = DefaultItemAnimator()
    }

    override fun firstRequest() {
        super.firstRequest()
        if (webSite.id == -1L) {//历史记录
            loadHistory()
        } else {
            //根据平台,获得推荐列表
            loadHotBySite(webSite)
        }
    }

    //根据站点,获得推荐
    private fun loadHotBySite(webSite: WebSite) {
        Thread {
            val html = Jsoup.parse(URL(webSite.url).openStream(), webSite.decode, webSite.url)
            when(webSite.name){
                "番茄推荐"->{
                    val parser = SiteParseFactory.create(webSite)

                }
            }

        }.start()
    }




    private fun loadHistory() {
        val data = DBHelper.loadSearchKeywords()
        loadResultSuccess(data)
    }

    /**
     * 数据加载成功,显示界面
     */
    private fun loadResultSuccess(data: MutableList<HistoryQuery>) {
        adapter.setNewData(data)
    }
}