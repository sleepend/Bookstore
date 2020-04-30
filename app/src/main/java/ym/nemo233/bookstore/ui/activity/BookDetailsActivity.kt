package ym.nemo233.bookstore.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_book_details.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.BookDetailsView
import ym.nemo233.bookstore.basic.loadUrl
import ym.nemo233.bookstore.presenter.BookDetailsPresenter
import ym.nemo233.bookstore.sqlite.PopularBooks
import ym.nemo233.bookstore.ui.adapter.BookstoreAdapter
import ym.nemo233.bookstore.utils.BlurTransform
import ym.nemo233.bookstore.utils.Te
import ym.nemo233.framework.YMMVPActivity
import ym.nemo233.framework.utils.L

/**
 *
 */
class BookDetailsActivity : YMMVPActivity<BookDetailsPresenter>(), BookDetailsView {
    private val popularBooks by lazy { intent.getParcelableExtra(BOOK_DETAILS) as PopularBooks }

    private val adapter by lazy { BookstoreAdapter() }

    override fun getLayoutId(): Int = R.layout.activity_book_details

    override fun createPresenter(): BookDetailsPresenter? = BookDetailsPresenter(this)

    companion object {
        private const val BOOK_DETAILS = "BOOK_DETAILS"
        fun skipTo(context: Context, popularBooks: PopularBooks) {
            val intent = Intent(context, BookDetailsActivity::class.java)
            intent.putExtra(BOOK_DETAILS, popularBooks)
            context.startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        L.d("[log] ${Te.toString(popularBooks)}")
        bd_book_name.text = popularBooks.bookName
        bd_book_auth.text = popularBooks.auth
        bd_book_classify.text = "分类 : ${popularBooks.typeName}"
        bd_book_source.text = "来源 : ${popularBooks.website}"
        bd_book_instr.text = popularBooks.instr
        bd_photo.loadUrl(popularBooks.picture)
        val options = RequestOptions.bitmapTransform(BlurTransform(this, 12f))
        bd_top_bg.loadUrl(popularBooks.picture, options)
    }

    override fun bindEvent() {
        super.bindEvent()
        bd_back.setOnClickListener { finish() }
    }

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadBookDetails(popularBooks)
    }

}