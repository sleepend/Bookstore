package ym.nemo233.bookstore.ui.dialog


import android.app.Dialog
import android.content.Context
import ym.nemo233.bookstore.MyApp

/**
 * Created by Android on 2018/8/23.
 */
open class IDialog : Dialog {
    constructor(context: Context) : super(context)
    constructor(context: Context, theme: Int) : super(context, theme)

    /**
     * 设置屏幕的宽高
     */
    fun setScreenWH(xp: Float, yp: Float) {
        val lp = window.attributes
        val x = MyApp.instance().displayMetrics.widthPixels
        val y = MyApp.instance().displayMetrics.widthPixels
        var width = 0
        var height = 0
        if (x < y) {
            width = x
            height = y
        } else {
            width = y
            height = x
        }
        lp.width = (width * xp).toInt()
        lp.height = (height * yp).toInt()
        window.attributes = lp
    }

    /**
     * 只改变弹出框宽度
     */
    fun setScreenWidth(xp: Float) {
        val lp = window.attributes
        lp.width = (MyApp.instance().displayMetrics.widthPixels * xp).toInt()
        window.attributes = lp
    }
}