package ym.nemo233.bookstore.widget.dialog

import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

open class IDialog : Dialog {
    constructor(context: Context) : super(context)
    constructor(context: Context, theme: Int) : super(context, theme)

    private val displayMetrics by lazy {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics
    }

    /**
     * 设置屏幕的宽高
     */
    fun setScreenWH(xp: Float, yp: Float) {
        val lp = window.attributes
        val x = displayMetrics.widthPixels
        val y = displayMetrics.heightPixels
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
        lp.width = (displayMetrics.widthPixels * xp).toInt()
        window.attributes = lp
    }


}