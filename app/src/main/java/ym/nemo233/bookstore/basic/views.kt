package ym.nemo233.bookstore.basic

import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.bookstore.sqlite.Chapter
import ym.nemo233.bookstore.sqlite.WebSite

interface ReaderView {
    fun onLoadBookByBookcase(
        chapter: Chapter?,
        durChapterIndex: Int,
        durPageIndex: Int,
        chaptersSize: Int
    )
}

interface BookcaseView {
    fun onLoadBookcase(data: List<BookInformation>)
}

interface BookstoreView {
    fun onLoadClassify(data: List<WebSite>?)
    fun onLoadFailed()
}

interface BookDetailsView {
    fun onAddToBookcase(result: Boolean)
    fun onUpdateBookInfo(bookInformation: BookInformation)
    /**
     * 加载失败
     */
    fun onLoadError()

    /**
     * 添加书架失败
     */
    fun appendFailed()
}

interface SearchBooksView {
    fun onResultBySearch(result: List<BookInformation>?)
}