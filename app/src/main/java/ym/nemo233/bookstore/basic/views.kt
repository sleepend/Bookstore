package ym.nemo233.bookstore.basic

import ym.nemo233.bookstore.beans.PopularBookArray

interface MainView
interface BookstoreView {
    fun onLoadBookstore(data: List<PopularBookArray>)
    fun onShowBooksSiteTitle(booksSiteName: String)
}
interface BookDetailsView