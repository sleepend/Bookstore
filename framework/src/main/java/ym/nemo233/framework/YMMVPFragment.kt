package ym.nemo233.framework

import ym.nemo233.framework.mvp.BasePresenter

/**
 * MVP模式Fragment基类
 */
abstract class YMMVPFragment<P : BasePresenter<*>> : YMFragment() {
    var mvp: P? = null

    abstract fun createPresenter(): P?

    override fun initializeBefore() {
        super.initializeBefore()
        mvp = createPresenter()
    }

    override fun onDestroy() {
        mvp?.detachView()
        super.onDestroy()
    }
}