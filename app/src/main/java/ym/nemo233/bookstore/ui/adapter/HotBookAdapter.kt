package ym.nemo233.bookstore.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.loadUrl
import ym.nemo233.bookstore.sqlite.HotBook

class HotBookAdapter :
    BaseQuickAdapter<HotBook, BaseViewHolder>(R.layout.item_search_hot, ArrayList()) {


    override fun convert(helper: BaseViewHolder?, item: HotBook) {
        helper?.apply {
            if (item.imgUrl.isNotEmpty()) {
                getView<ImageView>(R.id.item_bk_img).loadUrl(item.imgUrl)
            }
            setText(R.id.item_bk_name, item.name)
            setText(R.id.item_bk_auth, item.auth)
            setText(R.id.item_bk_newest, item.newest)
        }
    }
}