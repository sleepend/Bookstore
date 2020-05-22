package ym.nemo233.bookstore.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.sqlite.Chapter

/**
 * 书城
 */
class ChapterAdapter :
    BaseQuickAdapter<Chapter, BaseViewHolder>(R.layout.item_chapter) {

    override fun convert(helper: BaseViewHolder?, item: Chapter) {
        helper?.apply {
            setText(R.id.item_chapter_name, item.name)
            getView<TextView>(R.id.item_chapter_state).visibility = View.GONE
        }
    }
}