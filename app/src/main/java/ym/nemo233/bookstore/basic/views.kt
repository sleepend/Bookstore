package ym.nemo233.bookstore.basic

import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.bookstore.sqlite.Chapter
import ym.nemo233.bookstore.sqlite.WebSite

interface MainView {
    fun onLoadLocalBooks(books: MutableList<BookInformation>)
}

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

interface BookDetailsView {

    fun onUpdateBookInfo(bookInformation: BookInformation)
    /**
     * 加载失败
     */
    fun onLoadError()

    /**
     * 添加书架失败
     */
    fun appendFailed()

    fun appendSuccess()
}

interface SearchBooksView {
    fun onResultForLocalData(data: List<WebSite>)
}