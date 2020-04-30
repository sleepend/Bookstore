package ym.nemo233.bookstore

import android.view.View
import kotlinx.android.synthetic.main.activity_home.*
import ym.nemo233.bookstore.basic.MainView
import ym.nemo233.bookstore.presenter.MainPresenter
import ym.nemo233.bookstore.ui.dialog.SortPopupWindow
import ym.nemo233.bookstore.ui.fragments.BookcaseFragment
import ym.nemo233.bookstore.ui.fragments.BookstoreFragment
import ym.nemo233.framework.YMMVPActivity

class MainActivity : YMMVPActivity<MainPresenter>(), MainView {
    private val bookcaseFragment by lazy { BookcaseFragment() }
    private val bookstoreFragment by lazy { BookstoreFragment() }
    private val popSort by lazy {
        val pop = SortPopupWindow(this)
        pop.callback = object : SortPopupWindow.SortCallback {
            override fun selectSortStyle(sortStyle: Int, tag: String) {
                this@MainActivity.top_more.text = tag
            }
        }
        pop
    }
    private var currentPosition = 0//当前位置 0书架,1书城

    override fun getLayoutId(): Int = R.layout.activity_home
    override fun createPresenter(): MainPresenter? = MainPresenter(this)

    override fun initView() {
        //加载书架
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.home_frame, bookcaseFragment).commit()
        currentPosition = 0
    }

    override fun bindEvent() {
        super.bindEvent()
        top_bookcase.setOnClickListener {
            if (currentPosition != 0) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.hide(bookstoreFragment)
                transaction.show(bookcaseFragment).commitAllowingStateLoss()
                currentPosition = 0
            }
            top_more.visibility = View.VISIBLE
        }
        top_bookstore.setOnClickListener {
            if (currentPosition != 1) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.hide(bookcaseFragment)
                if (bookstoreFragment.isAdded) {
                    transaction.show(bookstoreFragment).commitAllowingStateLoss()
                } else {
                    transaction.add(R.id.home_frame, bookstoreFragment).commit()
                }
                currentPosition = 1
            }
            top_more.visibility = View.GONE
        }
        top_more.setOnClickListener {
            if (!popSort.isShowing) {
                popSort.showAsDropDown(top_layout, 16, 8)
            }
        }
        top_setup.setOnClickListener {
            //设置
        }
    }

    override fun firstRequest() {
        super.firstRequest()
        //加载分类
        mvp?.loadDefaultPopularBooks()
    }
}
