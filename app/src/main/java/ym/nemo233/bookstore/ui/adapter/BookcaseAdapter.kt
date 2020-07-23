package ym.nemo233.bookstore.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.basic.loadUrl
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.BookInformation

/**
 * 书城
 */
class BookcaseAdapter :
    BaseQuickAdapter<BookInformation, BaseViewHolder>(R.layout.item_bookcase) {

    override fun convert(helper: BaseViewHolder?, item: BookInformation) {
        helper?.apply {
            setText(R.id.item_bc_name, item.name)
            if (item.currentChapter == -1) {
                setText(R.id.item_bc_now, "未阅读")
            } else {
                val chapter = DBHelper.loadChapterByBook(item)
                setText(R.id.item_bc_now, "已读:${chapter.name}")
            }
            setText(R.id.item_bc_new, "最新:${item.newest}")
            setText(R.id.item_bc_tag, item.auth)
            getView<ImageView>(R.id.item_bc_img).loadUrl(item.imageUrl)
            if (item.theEnd == false) {
                GlobalScope.launch {
                    async {
                        val siteParser = SiteParseFactory.loadDefault(item.siteName)
                        siteParser?.checkUpdate(item)
                    }.await() ?: return@launch
                    notifyDataSetChanged()
                }
            }

        }
    }
}