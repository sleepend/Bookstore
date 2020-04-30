package ym.nemo233.bookstore.presenter

import org.jsoup.Jsoup
import ym.nemo233.bookstore.basic.BookDetailsView
import ym.nemo233.bookstore.basic.Share
import ym.nemo233.bookstore.sqlite.BooksInformation
import ym.nemo233.bookstore.sqlite.PopularBooks
import ym.nemo233.framework.mvp.BasePresenter
import java.net.URL

class BookDetailsPresenter(view: BookDetailsView) : BasePresenter<BookDetailsView>() {

    init {
        attachView(view)
    }

    fun loadBookDetails(bookInfo: PopularBooks) {
        val doc = Jsoup.parse(URL(bookInfo.bookUrl).openStream(), "GBK", bookInfo.bookUrl)
        val body = doc.body()
        val data = ArrayList<BooksInformation>()
        body.select("dd").map { dd ->
            val href = dd.select("a[href]")
            val tag = href.text()
            val url = href.attr("href")
            if (url.startsWith("http")) {
//                BooksInformation(null,bookInfo.bookName,bookInfo.auth,false,)
//                data.add(Chapter(tag, url))
            } else {
//                data.add(Chapter(tag, baseUrl + url))
            }
        }

    }
}