package ym.nemo233.framework.mvp

interface IPresenter<V> {

    fun attachView(v: V)
    fun detachView()
}