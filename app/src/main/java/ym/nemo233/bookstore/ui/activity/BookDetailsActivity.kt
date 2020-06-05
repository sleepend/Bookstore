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
import ym.nemo233.bookstore.presenter.BookDetailsPresenter
import ym.nemo233.bookstore.sqlite.BooksInformation
import ym.nemo233.bookstore.sqlite.Chapter
import ym.nemo233.bookstore.ui.adapter.ChapterAdapter
import ym.nemo233.bookstore.utils.BlurTransform
import ym.nemo233.bookstore.utils.Te
import ym.nemo233.framework.YMMVPActivity
import ym.nemo233.framework.utils.L

/**
 *
 */
class BookDetailsActivity : YMMVPActivity<BookDetailsPresenter>(), BookDetailsView {
    private val booksInformation by lazy { intent.getParcelableExtra(BOOK_DETAILS) as BooksInformation }

    private val adapter by lazy { ChapterAdapter() }

    override fun getLayoutId(): Int = R.layout.activity_book_details

    override fun createPresenter(): BookDetailsPresenter? = BookDetailsPresenter(this)

    companion object {
        private const val BOOK_DETAILS = "BOOK_DETAILS"
        fun skipTo(context: Context, booksInfo: BooksInformation) {
            val intent = Intent(context, BookDetailsActivity::class.java)
            intent.putExtra(BOOK_DETAILS, booksInfo)
            context.startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        L.d("[log] ${Te.toString(booksInformation)}")

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
                mvp?.addToBookcase(this@BookDetailsActivity, booksInformation)
            }
        }
        bd_start.setOnClickListener {
            //开始阅读
        }
    }

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadBookDetails(booksInformation)
    }

    @SuppressLint("SetTextI18n")
    override fun onUpdateBookInfo(booksInformation: BooksInformation) {
        L.v("[log-search] ${Te.toString(booksInformation)}")
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
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onLoadBookChapters(chapters: List<Chapter>?) {
        runOnUiThread {
            bd_book_newest.text = "最新章节: ${chapters?.first()?.name}"
            adapter.setNewData(chapters)
        }
    }

    override fun onAddToBookcase(result: Boolean) {
        bd_append.isEnabled = !result
        bd_append.tag = null
    }

}