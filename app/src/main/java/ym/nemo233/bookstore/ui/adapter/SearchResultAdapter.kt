package ym.nemo233.bookstore.ui.adapter

import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.sqlite.BookInformation

/**
 * 书城
 */
class SearchResultAdapter :
    BaseQuickAdapter<BookInformation, BaseViewHolder>(R.layout.item_search_result) {

    override fun convert(helper: BaseViewHolder?, item: BookInformation) {
        helper?.apply {
            setText(R.id.item_name, item.name)
            setText(R.id.item_auth, item.auth)
            val color = if (layoutPosition % 2 == 0) {
                ContextCompat.getColor(mContext, R.color.grey)
            } else {
                ContextCompat.getColor(mContext, R.color.white)
            }
            getView<LinearLayout>(R.id.item_search_bg).setBackgroundColor(color)
        }
    }
}