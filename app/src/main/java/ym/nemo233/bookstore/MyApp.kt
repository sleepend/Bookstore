package ym.nemo233.bookstore

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import ym.nemo233.bookstore.sqlite.DaoMaster
import ym.nemo233.framework.utils.L

class MyApp : Application() {

    companion object {
        private var app: MyApp? = null

        fun instance() = app!!
    }

    /**
     * 数据库管理器
     */
    val daoMaster by lazy {
        DaoMaster(DaoMaster.DevOpenHelper(instance(), "databases.db", null).writableDatabase)
    }
    val displayMetrics by lazy {
        val displayMetrics = DisplayMetrics()
        val wm = getSystemService(Context.WINDOW_SERVICE)as WindowManager
        wm.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        L.init(BuildConfig.DEBUG)
    }


}