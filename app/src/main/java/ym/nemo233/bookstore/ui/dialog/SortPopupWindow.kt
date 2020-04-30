package ym.nemo233.bookstore.ui.dialog

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.PopupWindow
import android.widget.TextView
import ym.nemo233.bookstore.R

class SortPopupWindow(context: Context) : PopupWindow(context) {
    var callback: SortCallback? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.pop_sort, null, false)
        view.findViewById<TextView>(R.id.pop_default).setOnClickListener {
            it as TextView
            callback?.selectSortStyle(0, it.text.toString())
            dismiss()
        }
        view.findViewById<TextView>(R.id.pop_all).setOnClickListener {
            it as TextView
            callback?.selectSortStyle(1, it.text.toString())
            dismiss()
        }
        this.contentView = view
        width = 240
        this.isFocusable = true
        isOutsideTouchable = true
        this.setBackgroundDrawable(ColorDrawable(0x0000000000))
        update()
    }

    interface SortCallback {
        fun selectSortStyle(sortStyle: Int, tag: String)
    }
}