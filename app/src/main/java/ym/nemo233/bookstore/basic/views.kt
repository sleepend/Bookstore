package ym.nemo233.bookstore.basic

import ym.nemo233.bookstore.beans.TempBook
import ym.nemo233.bookstore.sqlite.Bookcase
import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
import ym.nemo233.bookstore.sqlite.BooksInformation
import ym.nemo233.bookstore.sqlite.Chapter

interface ReaderView {
    fun onLoadBookByBookcase(
        chapter: Chapter?,
        durChapterIndex: Int,
        durPageIndex: Int,
        chaptersSize: Int
    )
}

interface BookcaseView {
    fun onLoadBookcase(data: List<Bookcase>)
}

interface BookstoreView {
    fun onLoadClassify(data: List<BookcaseClassifyCache>?)
    fun onLoadFailed()
}

interface BookDetailsView {
    fun onAddToBookcase(result: Boolean)
    fun onUpdateBookInfo(booksInformation: BooksInformation)
    /**
     * 加载失败
     */
    fun onLoadError()
}

interface SearchBooksView {
    fun onResultBySearch(result: List<TempBook>?)
}