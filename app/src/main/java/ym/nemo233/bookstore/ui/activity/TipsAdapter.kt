package ym.nemo233.bookstore.ui.activity

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ym.nemo233.bookstore.R

class TipsAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_tips, ArrayList()) {
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.apply {
            setText(R.id.item_tips_name, item)
        }
    }
}