package ym.nemo233.bookstore.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_book_details.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.BookDetailsView
import ym.nemo233.bookstore.basic.loadUrl
import ym.nemo233.bookstore.basic.toast
import ym.nemo233.bookstore.presenter.BookDetailsPresenter
import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.bookstore.sqlite.HotBook
import ym.nemo233.bookstore.ui.adapter.ChapterAdapter
import ym.nemo233.bookstore.utils.BlurTransform
import ym.nemo233.bookstore.utils.Te
import ym.nemo233.framework.YMMVPActivity
import ym.nemo233.framework.utils.L

/**
 *
 */
class BookDetailsActivity : YMMVPActivity<BookDetailsPresenter>(), BookDetailsView {
    private val hotBook by lazy { intent.getParcelableExtra(BOOK_INFORMATION) as HotBook }

    private val adapter by lazy { ChapterAdapter() }

    override fun getLayoutId(): Int = R.layout.activity_book_details

    override fun createPresenter(): BookDetailsPresenter? = BookDetailsPresenter(this)

    companion object {
        private const val BOOK_INFORMATION = "BOOK_INFORMATION"
        fun skipTo(context: Context, book: HotBook) {
            val intent = Intent(context, BookDetailsActivity::class.java)
            val bundle = Bundle().apply {
                putParcelable(BOOK_INFORMATION, book)
            }
            intent.putExtras(bundle)
            L.d("[data] ${Te.toString2(book)}")
            context.startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        bd_recycler.layoutManager = LinearLayoutManager(this)
        bd_recycler.adapter = adapter
        bd_recycler.itemAnimator = DefaultItemAnimator()
    }

    override fun bindEvent() {
        super.bindEvent()
        bd_back.setOnClickListener { finish() }
    }

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadBookDetails(hotBook)
    }

    @SuppressLint("SetTextI18n")
    override fun onUpdateBookInfo(bookInformation: BookInformation) {
        runOnUiThread {
            bd_book_name.text = bookInformation.name
            bd_book_auth.text = bookInformation.auth
            bd_book_classify.text = "分类 : ${bookInformation.className}"
            bd_book_source.text = "来源 : ${bookInformation.siteName}"
            bd_book_instr.text = bookInformation.instr
            bd_photo.loadUrl(bookInformation.imageUrl)
            val options = RequestOptions.bitmapTransform(BlurTransform(this, 12f))
            bd_top_bg.loadUrl(bookInformation.imageUrl, options)
            bd_book_newest.text = "最新章节: ${bookInformation.newest}"
            //
            adapter.setNewData(bookInformation.latest)

            bd_append.isEnabled = bookInformation.id == null
            bd_append.setOnClickListener {
                if (it.tag == null) {
                    it.tag = 1
                    mvp?.addToBookcase(bookInformation)
                }
            }
            bd_start.setOnClickListener {
                //开始阅读
            }
        }
    }

    override fun onLoadError() {
        //加载失败
        runOnUiThread {
            toast("加载失败")
        }
    }

    override fun appendFailed() {
        runOnUiThread {
            toast("添加失败")
        }
    }

    override fun appendSuccess() {
        runOnUiThread {
            bd_append.isEnabled = false
            bd_append.tag = null
        }
    }


}