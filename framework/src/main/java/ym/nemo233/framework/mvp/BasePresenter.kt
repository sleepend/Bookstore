package ym.nemo233.framework.mvp

/**
 * mvp模式-逻辑处理基类
 */
open class BasePresenter<V> : IPresenter<V> {
    var mvpView: V? = null

    override fun attachView(v: V) {
        this.mvpView = v
    }

    override fun detachView() {
        this.mvpView = null
    }

}