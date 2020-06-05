package ym.nemo233.bookstore.basic

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
    fun onLoadBookChapters(chapters: List<Chapter>?)
    fun onAddToBookcase(result: Boolean)
    fun onUpdateBookInfo(booksInformation: BooksInformation)
}

interface SearchBooksView {
    fun onResultBySearch(result: List<BooksInformation>?)
}