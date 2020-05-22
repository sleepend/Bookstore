package ym.nemo233.bookstore.presenter

import ym.nemo233.bookstore.basic.MainView
import ym.nemo233.framework.mvp.BasePresenter

class MainPresenter(view: MainView) : BasePresenter<MainView>() {

    init {
        attachView(view)
    }

}