package ym.nemo233.bookstore.ui.activity

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_reader.*
import ym.nemo233.bookstore.R
import ym.nemo233.bookstore.basic.ReaderView
import ym.nemo233.bookstore.presenter.ReaderPresenter
import ym.nemo233.bookstore.sqlite.Bookcase
import ym.nemo233.bookstore.sqlite.Chapter
import ym.nemo233.bookstore.widget.BookContentView
import ym.nemo233.bookstore.widget.ContentSwitchView
import ym.nemo233.bookstore.widget.ReaderController
import ym.nemo233.framework.YMMVPActivity
import ym.nemo233.framework.utils.L

/**
 *
 */
class ReaderActivity : YMMVPActivity<ReaderPresenter>(), ReaderView,
    ContentSwitchView.OnBookReadInitListener {
    private val inType by lazy { intent.getIntExtra(IN_TYPE, 0) }

    override fun createPresenter(): ReaderPresenter? = ReaderPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_reader

    companion object {
        private const val BK = "BK"
        private const val IN_TYPE = "IN_TYPE"

        fun skipTo(context: Context, bookcase: Bookcase) {
            val intent = Intent(context, ReaderActivity::class.java)
            intent.putExtra(BK, bookcase)
            intent.putExtra(IN_TYPE, 0)
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
        book_content.bookReaderInit(this)
    }

    override fun bindEvent() {
        super.bindEvent()

        reader_btm_mod.setOnClickListener {
            ReaderController.getInstance().nightModeReversal()
            book_content.changeBackground()
        }
        reader_pre.setOnClickListener {
            //            val chapterIndex = book_content.currentChapterIndex
//            book_content.moveToPreChapter(data[chapterIndex].data.size)
        }
        reader_next.setOnClickListener {
            //            val chapterIndex = book_content.currentChapterIndex
//            book_content.moveToNextChapter(data[chapterIndex].data.size)
        }
    }


    override fun updateProgress(durChapterIndex: Int, durPageIndex: Int) {
        L.d("[log-page] $durChapterIndex / $durPageIndex")
    }

    override fun initData(lineCount: Int) {
        if (inType == 0) {
            val bookcase = intent.getParcelableExtra<Bookcase>(BK)
            mvp?.loadBookByBookcase(bookcase, lineCount)
        } else {

        }
    }

    override fun getChapterTitle(chapterIndex: Int): String {
        return "章节 $chapterIndex"
    }

    override fun noPrePage() {
        Toast.makeText(this, "没有上一页", Toast.LENGTH_SHORT).show()
    }

    override fun noNextPage() {
        Toast.makeText(this, "没有下一页", Toast.LENGTH_SHORT).show()
    }

    override fun showOrHideMenu(isShowMenu: Boolean) {
        if (isShowMenu) {
            reader_top_bar.visibility = View.VISIBLE
            reader_btm.visibility = View.VISIBLE
        } else {
            reader_top_bar.visibility = View.GONE
            reader_btm.visibility = View.GONE
        }
    }

    override fun noChapter(durChapterIndex: Int, chapterAll: Int) {
        if (durChapterIndex == 0 && durChapterIndex == chapterAll - 1) {
            reader_next.isEnabled = false
            reader_pre.isEnabled = false
        } else {
            reader_next.isEnabled = durChapterIndex < chapterAll - 1
            reader_pre.isEnabled = durChapterIndex > 0
        }
    }

    override fun loadData(
        bookContentView: BookContentView,
        durChapterIndex: Int,
        durPageIndex: Int
    ) {
        L.d("[log-]$durChapterIndex | $durPageIndex")
        val chapter = mvp?.loadChapter(durChapterIndex)
        if(chapter==null) {
            bookContentView.loadError()
        }else {
            val content = when(durPageIndex){
                ContentSwitchView.DURPAGEINDEXBEGIN->chapter.data[0]
                ContentSwitchView.DURPAGEINDEXEND->chapter.data.last()
                else->chapter.data[durPageIndex]
            }
            bookContentView.updateData(chapter.name,content,durChapterIndex,1000,durPageIndex,chapter.data.size)
        }
//        val pageIndex = when (durPageIndex) {
//            ContentSwitchView.DURPAGEINDEXBEGIN -> 0//向下一页
//            ContentSwitchView.DURPAGEINDEXEND -> data[durChapterIndex].data.size - 1//向上
//            else -> durPageIndex
//        }
//        bookContentView.updateData(
//            data[durChapterIndex].name,
//            data[durChapterIndex].data[pageIndex],
//            durChapterIndex,
//            data.size,
//            pageIndex,
//            data[durChapterIndex].data.size
//        )
    }

    override fun success() {
        //加载控件完成//权限检查
        book_content.startLoading()//加载
    }

    override fun onLoadBookByBookcase(
        chapter: Chapter?,
        durChapterIndex: Int,
        durPageIndex: Int,
        chaptersSize: Int
    ) {
        mvp?.splitContent(book_content, chapter)
        runOnUiThread {
            book_content.setInitData(durChapterIndex, chaptersSize, durPageIndex)
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return book_content.onKeyUp(keyCode, event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//拓展->添加到书架
            finish()
            return true
        }
        return book_content.onKeyDown(keyCode, event)
    }


}