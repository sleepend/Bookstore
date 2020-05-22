package ym.nemo233.bookstore.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.basic.MyApp
import ym.nemo233.bookstore.basic.loadUrl
import ym.nemo233.bookstore.sqlite.Bookcase

/**
 * 书城
 */
class BookcaseAdapter :
    BaseQuickAdapter<Bookcase, BaseViewHolder>(R.layout.item_bookcase) {

    override fun convert(helper: BaseViewHolder?, item: Bookcase) {
        helper?.apply {
            item.__setDaoSession(MyApp.instance().daoSession)
            setText(R.id.item_bc_name, item.book.name)
            if (item.chapter == -1) {
                setText(R.id.item_bc_now, "未阅读")
            } else {
                val chapter = DBHelper.loadChapterByBook(item.book)
                setText(R.id.item_bc_now, "已读:${chapter.name}")
            }
            setText(R.id.item_bc_new, "最新:${item.newest}")
            setText(R.id.item_bc_tag, item.book.auth)
            getView<ImageView>(R.id.item_bc_img).loadUrl(item.book.imageUrl)
        }
    }
}