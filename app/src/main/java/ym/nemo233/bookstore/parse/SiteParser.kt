package ym.nemo233.bookstore.parse

import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
import ym.nemo233.bookstore.sqlite.BooksSite

interface SiteParser {
    fun loadBookcaseClassify(booksSite: BooksSite): List<BookcaseClassifyCache>?
}