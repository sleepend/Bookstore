package ym.nemo233.bookstore.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.sqlite.HistoryQuery

class SearchTipsAdapter :
    BaseQuickAdapter<HistoryQuery, BaseViewHolder>(R.layout.item_search_tips, ArrayList()) {

    override fun convert(helper: BaseViewHolder?, item: HistoryQuery) {
        helper?.setText(R.id.item_keywords, item.searchKey)
    }
}