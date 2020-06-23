package ym.nemo233.bookstore.beans

import java.io.Serializable

/**
 * 书城返回的临时对象
 */
data class TempBook(
    val name: String,
    val auth: String,
    val imageUrl: String,
    val classifyName: String,
    val sourceUrl: String,
    val site: String,
    val isSearchResult:Boolean
) : Serializable
