package ym.nemo233.bookstore.ui.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.loadUrl
import ym.nemo233.bookstore.beans.PopularBookArray
import ym.nemo233.bookstore.utils.Te
import ym.nemo233.framework.utils.L

/**
 * 书城
 */
class BookstoreAdapter :
    BaseQuickAdapter<PopularBookArray, BaseViewHolder>(R.layout.item_bookstore) {

    override fun convert(helper: BaseViewHolder?, item: PopularBookArray) {
        helper?.apply {
            setText(R.id.item_bs_title, item.type)
            //1
            setText(R.id.item_layout_1_name, item.data[0].bookName)
            setText(R.id.item_layout_1_auth, item.data[0].auth)
            getView<ImageView>(R.id.item_layout_1_img).loadUrl(item.data[0].picture)
            //2
            setText(R.id.item_layout_2_name, item.data[1].bookName)
            setText(R.id.item_layout_2_auth, item.data[1].auth)
            getView<ImageView>(R.id.item_layout_2_img).loadUrl(item.data[1].picture)
            //3
            setText(R.id.item_layout_3_name, item.data[2].bookName)
            setText(R.id.item_layout_3_auth, item.data[2].auth)
            getView<ImageView>(R.id.item_layout_3_img).loadUrl(item.data[2].picture)
            //4
            setText(R.id.item_layout_4_name, item.data[3].bookName)
            setText(R.id.item_layout_4_auth, item.data[3].auth)
            getView<ImageView>(R.id.item_layout_4_img).loadUrl(item.data[3].picture)
            //事件
            addOnClickListener(R.id.item_bs_more)
            addOnClickListener(R.id.item_layout_1)
            addOnClickListener(R.id.item_layout_2)
            addOnClickListener(R.id.item_layout_3)
            addOnClickListener(R.id.item_layout_4)
            L.d("[log] ${Te.toString(item.data[0])}")
            //
            if (layoutPosition == itemCount - 1) {
                getView<View>(R.id.item_layout_5).visibility = View.VISIBLE
            } else {
                getView<View>(R.id.item_layout_5).visibility = View.GONE
            }
        }
    }
}