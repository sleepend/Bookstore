package ym.nemo233.bookstore.basic

import ym.nemo233.bookstore.beans.PopularBookArray
import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache

interface MainView
interface BookstoreView {
    fun onLoadBookstore(data: List<PopularBookArray>)
    fun onShowBooksSiteTitle(booksSiteName: String)
    fun onLoadClassify(data: List<BookcaseClassifyCache>?)
}
interface BookDetailsView