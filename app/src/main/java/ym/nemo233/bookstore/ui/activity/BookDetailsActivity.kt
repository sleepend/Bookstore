package ym.nemo233.bookstore.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_book_details.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.BookDetailsView
import ym.nemo233.bookstore.basic.loadUrl
import ym.nemo233.bookstore.beans.TempBook
import ym.nemo233.bookstore.presenter.BookDetailsPresenter
import ym.nemo233.bookstore.sqlite.BooksInformation
import ym.nemo233.bookstore.ui.adapter.ChapterAdapter
import ym.nemo233.bookstore.utils.BlurTransform
import ym.nemo233.framework.YMMVPActivity

/**
 *
 */
class BookDetailsActivity : YMMVPActivity<BookDetailsPresenter>(), BookDetailsView {
    private val tempBook by lazy { intent.getSerializableExtra(TEMP_BOOK) as TempBook }

    private val adapter by lazy { ChapterAdapter() }

    override fun getLayoutId(): Int = R.layout.activity_book_details

    override fun createPresenter(): BookDetailsPresenter? = BookDetailsPresenter(this)

    companion object {
        private const val TEMP_BOOK = "TEMP_BOOK"
        fun skipTo(context: Context, tempBook: TempBook) {
            val intent = Intent(context, BookDetailsActivity::class.java)
            intent.putExtra(TEMP_BOOK, tempBook)
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
        bd_append.setOnClickListener {
            if (it.tag == null) {
                it.tag = 1
                mvp?.addToBookcase()
            }
        }
        bd_start.setOnClickListener {
            //开始阅读
        }
    }

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadBookDetails(tempBook)
    }

    @SuppressLint("SetTextI18n")
    override fun onUpdateBookInfo(booksInformation: BooksInformation) {
        runOnUiThread {
            bd_book_name.text = booksInformation.name
            bd_book_auth.text = booksInformation.auth
            bd_append.isEnabled = mvp?.checkIsAppendBookcase(booksInformation) ?: true
            bd_book_classify.text = "分类 : ${booksInformation.className}"
            bd_book_source.text = "来源 : ${booksInformation.baseUrl}"
            bd_book_instr.text = booksInformation.instr
            bd_photo.loadUrl(booksInformation.imageUrl)
            val options = RequestOptions.bitmapTransform(BlurTransform(this, 12f))
            bd_top_bg.loadUrl(booksInformation.imageUrl, options)
            bd_book_newest.text = "最新章节: ${booksInformation.chapters.first()?.name}"
            //
            adapter.setNewData(booksInformation.chapters)
        }
    }

    override fun onLoadError() {
        //加载失败

    }

    override fun onAddToBookcase(result: Boolean) {
        runOnUiThread {
            bd_append.isEnabled = !result
            bd_append.tag = null
        }
    }

}