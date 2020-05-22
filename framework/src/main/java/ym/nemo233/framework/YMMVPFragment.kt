package ym.nemo233.framework

import android.view.LayoutInflater
import android.view.View
import ym.nemo233.framework.mvp.BasePresenter

/**
 * MVP模式Fragment基类
 */
abstract class YMMVPFragment<P : BasePresenter<*>> : YMFragment() {
    var mvp: P? = null

    private lateinit var loadView: View

    abstract fun createPresenter(): P?

    override fun initializeBefore() {
        super.initializeBefore()
        mvp = createPresenter()
        //可添加载动态view
        loadView = LayoutInflater.from(context).inflate(R.layout.view_loading, null, false)
        loadView.setOnClickListener {
            //屏蔽点击事件
        }
        loadView.visibility = View.GONE
        rootView.addView(loadView)
    }

    fun showLoadingView() {
        loadView.visibility = View.VISIBLE
    }

    fun hideLoadingView() {
        loadView.visibility = View.GONE
    }

    override fun onDestroy() {
        mvp?.detachView()
        super.onDestroy()
    }
}