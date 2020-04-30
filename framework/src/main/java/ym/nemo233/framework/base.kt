package ym.nemo233.framework

interface YMBaseInterface {
    fun getLayoutId(): Int
    fun initializeBefore()
    fun initView()
    fun bindEvent()
    fun firstRequest()
}
