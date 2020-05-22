package ym.nemo233.bookstore.widget.pop

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.PopupWindow
import android.widget.TextView
import ym.nemo233.bookstore.R

class TypePopWindow(context: Context) : PopupWindow(context) {
    var callback: YMPopCallback? = null

    init {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.pop_book_type, null, false)
        view.findViewById<TextView>(R.id.pop_bt_def).setOnClickListener {
            callback?.select(0)
            dismiss()
        }
        view.findViewById<TextView>(R.id.pop_bt_all).setOnClickListener {
            callback?.select(1)
            dismiss()
        }
        this.contentView = view
        width = dp2px(context, 72f)
        this.isFocusable = true
        isOutsideTouchable = true
        this.setBackgroundDrawable(ColorDrawable(0x0000000000))
        update()
    }

    /**
     * dp转换成px
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    interface YMPopCallback {
        fun select(type: Int)
    }
}