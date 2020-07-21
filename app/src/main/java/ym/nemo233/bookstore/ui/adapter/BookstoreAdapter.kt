package ym.nemo233.bookstore.ui.adapter

import android.app.Activity
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.loadUrl
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.WebSite

/**
 * 书城
 */
class BookstoreAdapter(private val activity: Activity) :
    BaseQuickAdapter<WebSite, BaseViewHolder>(R.layout.item_bookstore) {

//    private val siteParser by lazy { SiteParseFactory.loadDefault() }

    override fun convert(helper: BaseViewHolder?, item: WebSite) {
        helper?.apply {
            setText(R.id.item_bs_title, item.name)
            Thread {
//                item.books = siteParser.loadBooksByClassify(item)
                if (item.books != null) {
                    activity.runOnUiThread {
                        addOnClickListener(R.id.item_bs_more)//更多
                        if (item.books.isNotEmpty()) {
                            addOnClickListener(R.id.item_layout_1)
                            setText(R.id.item_layout_1_name, item.books.first().name)
                            setText(R.id.item_layout_1_auth, item.books.first().auth)
                            getView<ImageView>(R.id.item_layout_1_img).loadUrl(item.books.first().imageUrl)
                        }
                        if (item.books.size > 1) {
                            addOnClickListener(R.id.item_layout_2)
                            setText(R.id.item_layout_2_name, item.books[1].name)
                            setText(R.id.item_layout_2_auth, item.books[1].auth)
                            getView<ImageView>(R.id.item_layout_2_img).loadUrl(item.books[1].imageUrl)
                        }
                        if (item.books.size > 2) {
                            addOnClickListener(R.id.item_layout_3)
                            setText(R.id.item_layout_3_name, item.books[2].name)
                            setText(R.id.item_layout_3_auth, item.books[2].auth)
                            getView<ImageView>(R.id.item_layout_3_img).loadUrl(item.books[2].imageUrl)
                        }
                        if (item.books.size > 3) {
                            addOnClickListener(R.id.item_layout_4)
                            setText(R.id.item_layout_4_name, item.books[3].name)
                            setText(R.id.item_layout_4_auth, item.books[3].auth)
                            getView<ImageView>(R.id.item_layout_4_img).loadUrl(item.books[3].imageUrl)
                        }
                    }
                }
            }.start()
        }
    }
}