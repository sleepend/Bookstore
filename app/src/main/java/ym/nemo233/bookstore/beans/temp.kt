package ym.nemo233.bookstore.beans

import ym.nemo233.bookstore.sqlite.PopularBooks

/**
 * 列表的临时对象
 */
data class PopularBookArray(val type: String, val data: ArrayList<PopularBooks>)