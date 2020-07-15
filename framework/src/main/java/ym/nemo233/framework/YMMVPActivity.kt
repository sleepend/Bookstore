package ym.nemo233.framework

import ym.nemo233.framework.mvp.BasePresenter

/**
 * MVP模式基类
 */
abstract class YMMVPActivity<P : BasePresenter<*>> : YMActivity() {
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

    override fun initView() {

    }
}