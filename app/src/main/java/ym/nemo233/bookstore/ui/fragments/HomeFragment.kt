package ym.nemo233.bookstore.ui.fragments

import ym.nemo233.bookstore.R
import ym.nemo233.framework.YMFragment
import ym.nemo233.framework.utils.L

/**
 * 主页
 */
class HomeFragment : YMFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        L.e("[llll] create view ${this::class.java.simpleName}")
    }
}