package ym.nemo233.bookstore.ui.fragments

import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.sqlite.WebSite
import ym.nemo233.framework.YMFragment

/**
 * 搜索的提示符
 */
class SearchTipsFragment : YMFragment() {
    private val webSite by lazy { arguments?.getParcelable(WEB_SITE) as WebSite }

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

    }

    override fun firstRequest() {
        super.firstRequest()

    }
}