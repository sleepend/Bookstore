package ym.nemo233.bookstore.ui.adapter

import android.app.Activity
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.loadUrl
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache

/**
 * 书城
 */
class BookstoreAdapter(private val activity: Activity) :
    BaseQuickAdapter<BookcaseClassifyCache, BaseViewHolder>(R.layout.item_bookstore) {

    private val siteParser by lazy { SiteParseFactory.loadDefault() }

    override fun convert(helper: BaseViewHolder?, item: BookcaseClassifyCache) {
        helper?.apply {
            setText(R.id.item_bs_title, item.name)
            Thread {
                siteParser.loadBooksByClassify(item)
                activity.runOnUiThread {
                    setText(R.id.item_layout_1_name, item.books[0].name)
                    setText(R.id.item_layout_1_auth, item.books[0].auth)
                    //2
                    setText(R.id.item_layout_2_name, item.books[1].name)
                    setText(R.id.item_layout_2_auth, item.books[1].auth)
                    //3
                    setText(R.id.item_layout_3_name, item.books[2].name)
                    setText(R.id.item_layout_3_auth, item.books[2].auth)
                    //4
                    setText(R.id.item_layout_4_name, item.books[3].name)
                    setText(R.id.item_layout_4_auth, item.books[3].auth)
                    //1
                    getView<ImageView>(R.id.item_layout_1_img).loadUrl(item.books[0].imageUrl)
                    getView<ImageView>(R.id.item_layout_2_img).loadUrl(item.books[1].imageUrl)
                    getView<ImageView>(R.id.item_layout_3_img).loadUrl(item.books[2].imageUrl)
                    getView<ImageView>(R.id.item_layout_4_img).loadUrl(item.books[3].imageUrl)
                    //事件
                    addOnClickListener(R.id.item_bs_more)
                    addOnClickListener(R.id.item_layout_1)
                    addOnClickListener(R.id.item_layout_2)
                    addOnClickListener(R.id.item_layout_3)
                    addOnClickListener(R.id.item_layout_4)
                }
            }.start()
        }
    }
}