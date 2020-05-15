package ym.nemo233.bookstore

import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.main_view.*
import ym.nemo233.bookstore.basic.MainView
import ym.nemo233.bookstore.basic.MyApp
import ym.nemo233.bookstore.presenter.MainPresenter
import ym.nemo233.framework.YMMVPActivity
import ym.nemo233.framework.utils.L

/**
 * 需要增加引导页 splash
 */
class MainActivity : YMMVPActivity<MainPresenter>(), MainView {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun createPresenter(): MainPresenter? = MainPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun initView() {
        setSupportActionBar(toolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_bookstore, R.id.nav_coll, R.id.nav_settings, R.id.nav_about
            ), drawer_layout
        )

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
        //
        MyApp.instance().initData()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        L.e("[llll] ${item?.title}")
        return super.onOptionsItemSelected(item)
    }
}
