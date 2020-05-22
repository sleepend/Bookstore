package ym.nemo233.bookstore

import kotlinx.android.synthetic.main.activity_main.*
import ym.nemo233.bookstore.ui.fragments.BookstoreFragment
import ym.nemo233.bookstore.ui.fragments.HomeFragment
import ym.nemo233.bookstore.ui.fragments.MeFragment
import ym.nemo233.framework.YMActivity
import ym.nemo233.framework.YMFragment

/**
 * 需要增加引导页 splash
 */
class MainActivity : YMActivity() {
    private val fragments by lazy {
        ArrayList<YMFragment>().apply {
            add(HomeFragment())
            add(BookstoreFragment())
            add(MeFragment())
        }
    }
    private var currentIndex = 0;

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame_layout, fragments[0]).commit()
        main_group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.main_home -> showOrHideFragment(0)
                R.id.main_store -> showOrHideFragment(1)
                R.id.main_other -> showOrHideFragment(2)
            }
        }
    }

    private fun showOrHideFragment(nowIndex: Int) {
        val ft = supportFragmentManager.beginTransaction()
        ft.hide(fragments[currentIndex])
        if (!fragments[nowIndex].isAdded) {
            ft.add(R.id.frame_layout, fragments[nowIndex])
        }
        ft.show(fragments[nowIndex])
        currentIndex = nowIndex
        ft.commitAllowingStateLoss()
    }

}
