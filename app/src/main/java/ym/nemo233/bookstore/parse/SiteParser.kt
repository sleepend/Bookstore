package ym.nemo233.bookstore.parse

import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.bookstore.sqlite.Chapter
import ym.nemo233.bookstore.sqlite.WebSite

interface SiteParser {
    /**
     * 返回默认书城编号
     */
    fun getWebSiteId(): Long

    /**
     * 加载书城分类
     */
    fun loadBookcaseClassify(): List<WebSite>?

    /**
     * 解析分类获得列表
     */
    fun loadBooksByClassify(webSite: WebSite): List<BookInformation>?

    /**
     * 加载详情&最新章节
     */
    fun loadBookInformation(bookInformation: BookInformation)

    /**
     * 缓存目录
     * @return 最新章节
     */
    fun loadChaptersCache(bookInformation: BookInformation): Boolean

    /**
     * 解析章节
     */
    fun loadChapter(chapter: Chapter): Chapter

    /**
     * 搜索
     */
    fun searchBook(bookName: String): List<BookInformation>?

}