package ym.nemo233.bookstore.ui.activity

import android.content.Context
import android.content.Intent
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_reader.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.ReaderView
import ym.nemo233.bookstore.presenter.ReaderPresenter
import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.bookstore.sqlite.Chapter
import ym.nemo233.framework.YMMVPActivity

/**
 *
 */
class ReaderActivity : YMMVPActivity<ReaderPresenter>(), ReaderView {
    private val book by lazy { intent.getParcelableExtra(KEY_BOOK) as BookInformation }

    override fun createPresenter(): ReaderPresenter? = ReaderPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_reader

    companion object {
        private const val KEY_BOOK = "BOOK"

        fun skipTo(context: Context, book: BookInformation) {
            val intent = Intent(context, ReaderActivity::class.java)
            intent.putExtra(KEY_BOOK, book)
            context.startActivity(intent)
        }
    }

    override fun initializeBefore() {
        super.initializeBefore()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun initView() {
    }

    override fun firstRequest() {
        super.firstRequest()
        mvp?.loadBookChapters(book)
    }

    override fun bindEvent() {
        super.bindEvent()
        reader_btm_mod.setOnClickListener {

        }
        reader_pre.setOnClickListener {
            mvp?.move2Pre()
        }
        reader_next.setOnClickListener {
            mvp?.move2Next()
        }
    }

    override fun onLoadBookByBookcase(
        chapter: Chapter?,
        durChapterIndex: Int,
        durPageIndex: Int,
        chaptersSize: Int
    ) {

    }
}