package ym.nemo233.bookstore.parse

import ym.nemo233.bookstore.parse.impl.FqxsSiteParser
import ym.nemo233.bookstore.sqlite.BooksSite

object SiteParseFactory {

    fun create(booksSite: BooksSite): SiteParser {
        return FqxsSiteParser(booksSite)
    }

}