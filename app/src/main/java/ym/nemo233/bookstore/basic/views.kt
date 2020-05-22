package ym.nemo233.bookstore.basic

import ym.nemo233.bookstore.beans.PopularBookArray
import ym.nemo233.bookstore.sqlite.Bookcase
import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
import ym.nemo233.bookstore.sqlite.BooksInformation
import ym.nemo233.bookstore.sqlite.Chapter

interface MainView

interface BookcaseView{
    fun onLoadBookcase(data: List<Bookcase>)
}

interface BookstoreView {
    fun onLoadBookstore(data: List<PopularBookArray>)
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