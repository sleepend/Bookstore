package ym.nemo233.bookstore.ui.activity

import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.BaseActivity
import ym.nemo233.bookstore.basic.DBHelper

/**
 * 需要增加引导页 splash
 */
class MainActivity : BaseActivity(){//<MainPresenter>(), MainView {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {

    }
//    override fun createPresenter(): MainPresenter? = MainPresenter(this)

//    override fun initView() {
//        super.initView()
//    }

    override fun bindEvent() {
        super.bindEvent()
        about.setOnClickListener {

        }
        search.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
    }

    override fun firstRequest() {
        super.firstRequest()
//        mvp?.loadLocalBooks()
        DBHelper.initializationConfiguration()
    }

}
