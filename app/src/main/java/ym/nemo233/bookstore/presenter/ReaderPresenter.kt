package ym.nemo233.bookstore.presenter

import android.text.Layout
import android.text.StaticLayout
import ym.nemo233.bookstore.basic.MyApp
import ym.nemo233.bookstore.basic.ReaderView
import ym.nemo233.bookstore.parse.SiteParseFactory
import ym.nemo233.bookstore.sqlite.Bookcase
import ym.nemo233.bookstore.sqlite.Chapter
import ym.nemo233.bookstore.utils.Te
import ym.nemo233.bookstore.widget.ContentSwitchView
import ym.nemo233.framework.mvp.BasePresenter
import ym.nemo233.framework.utils.L

class ReaderPresenter(view: ReaderView) : BasePresenter<ReaderView>() {
    private val siteParser by lazy { SiteParseFactory.loadDefault() }
    private var lineCount = 0

    init {
        attachView(view)
    }

    fun loadBookByBookcase(bookcase: Bookcase?, lineCount: Int) {
        bookcase ?: return
        this.lineCount = lineCount
        Thread {
            bookcase.__setDaoSession(MyApp.instance().daoSession)
            bookcase.book.__setDaoSession(MyApp.instance().daoSession)
            val durChapterIndex = if (bookcase.chapter < 0) 0 else bookcase.chapter
            val chapter = bookcase.book.chapters[durChapterIndex]
            siteParser.loadChapter(chapter)
            val durPageIndex = if (bookcase.pageNumber < 0) 0 else bookcase.pageNumber
            mvpView?.onLoadBookByBookcase(
                chapter,
                durChapterIndex,
                bookcase.book.chapters.size,
                durPageIndex
            )
        }.start()

    }

    /**
     * 分割文本内容
     */
    fun splitContent(
        view: ContentSwitchView,
        chapter: Chapter?
    ) {
        if (chapter?.data?.isEmpty() == true) {
            val paint = view.textPaint
            paint.isSubpixelText = true

            val tempLayout = StaticLayout(
                chapter.content,
                paint,
                view.contentWidth,
                Layout.Alignment.ALIGN_NORMAL,
                0f,
                0f,
                false
            )
            var lst = ArrayList<String>()
            for (i in 0 until tempLayout.lineCount) {
                if (lst.size % lineCount == 0) {
                    lst = ArrayList()//分页
                    chapter.data.add(lst)
                }
                lst.add(
                    chapter.content.substring(
                        tempLayout.getLineStart(i),
                        tempLayout.getLineEnd(i)
                    )
                )
                if (i == 0) {
                    lst.add("")
                    lst.add("")
                }
            }
        }
    }

    fun loadChapter(durChapterIndex: Int): Chapter? {

        return null
    }

}