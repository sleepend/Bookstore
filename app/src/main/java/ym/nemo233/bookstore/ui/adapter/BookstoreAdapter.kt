package ym.nemo233.bookstore.ui.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.loadUrl
import ym.nemo233.bookstore.beans.PopularBookArray
import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
import ym.nemo233.bookstore.utils.Te
import ym.nemo233.framework.utils.L

/**
 * 书城
 */
class BookstoreAdapter :
    BaseQuickAdapter<BookcaseClassifyCache, BaseViewHolder>(R.layout.item_bookstore) {

    override fun convert(helper: BaseViewHolder?, item: BookcaseClassifyCache) {
        helper?.apply {
            setText(R.id.item_bs_title, item.name)
            //1
            setText(R.id.item_layout_1_name, item.books[0].name)
            setText(R.id.item_layout_1_auth, item.books[0].auth)
            getView<ImageView>(R.id.item_layout_1_img).loadUrl(item.books[0].imageUrl)
            //2
            setText(R.id.item_layout_2_name, item.books[1].name)
            setText(R.id.item_layout_2_auth, item.books[1].auth)
            getView<ImageView>(R.id.item_layout_2_img).loadUrl(item.books[1].imageUrl)
            //3
            setText(R.id.item_layout_3_name, item.books[2].name)
            setText(R.id.item_layout_3_auth, item.books[2].auth)
            getView<ImageView>(R.id.item_layout_3_img).loadUrl(item.books[2].imageUrl)
            //4
            setText(R.id.item_layout_4_name, item.books[3].name)
            setText(R.id.item_layout_4_auth, item.books[3].auth)
            getView<ImageView>(R.id.item_layout_4_img).loadUrl(item.books[3].imageUrl)
            //事件
            addOnClickListener(R.id.item_bs_more)
            addOnClickListener(R.id.item_layout_1)
            addOnClickListener(R.id.item_layout_2)
            addOnClickListener(R.id.item_layout_3)
            addOnClickListener(R.id.item_layout_4)
            L.d("[log] ${Te.toString(item.books[0])}")
            //
            if (layoutPosition == itemCount - 1) {
                getView<View>(R.id.item_layout_5).visibility = View.VISIBLE
            } else {
                getView<View>(R.id.item_layout_5).visibility = View.GONE
            }
        }
    }
}