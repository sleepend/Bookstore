package ym.nemo233.bookstore.ui.activity

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.top_bar_search.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.SearchBooksView
import ym.nemo233.bookstore.presenter.SearchPresenter
import ym.nemo233.bookstore.sqlite.WebSite
import ym.nemo233.bookstore.ui.fragments.HotBooksFragment
import ym.nemo233.bookstore.ui.fragments.SearchTipsFragment
import ym.nemo233.framework.YMMVPActivity

/**
 * viewpager2
 * https://www.jianshu.com/p/93e2850cc480
 */
class SearchActivity : YMMVPActivity<SearchPresenter>(), SearchBooksView {

    override fun getLayoutId(): Int = R.layout.activity_search
    override fun createPresenter(): SearchPresenter? = SearchPresenter(this)

    override fun initView() {
        super.initView()
    }

    override fun bindEvent() {
        super.bindEvent()
        topbar_back.setOnClickListener { finish() }
        topbar_clear.setOnClickListener {
            topbar_search_view.setText("")
        }
        topbar_search.setOnClickListener {
            //搜索
            val keywords = topbar_search_view.text.toString()
            if (keywords.isEmpty()) {
                return@setOnClickListener
            }
            SearchResultActivity.skipTo(this@SearchActivity, keywords)
        }
    }

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadSiteList()
    }

    override fun onResultForLocalData(data: List<WebSite>) {
        search_view_pager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = data.size

            override fun createFragment(position: Int): Fragment {
                return if (position == 0) {
                    SearchTipsFragment.getInstance()
                } else {
                    HotBooksFragment.getInstance(data[position])
                }
            }
        }
        TabLayoutMediator(
            search_tab_layout,
            search_view_pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                //tab 当前选中对象
                //position 当前选的位置
                tab.text = data[position].name
            }).attach()
        search_view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            //滑动切换事件
        })
    }

}
