package ym.nemo233.bookstore.parse

import ym.nemo233.bookstore.sqlite.BookInformation
import ym.nemo233.bookstore.sqlite.Chapter
import ym.nemo233.bookstore.sqlite.HotBook

interface SiteParser {
    /**
     * 返回热门
     */
    fun loadHotBooks(): List<HotBook>

    /**
     * 加载详情&最新章节
     */
    fun loadBookInformation(hotBook: HotBook): BookInformation?

    /**
     * 缓存章节目录
     */
    fun loadAllChapters(bookInformation: BookInformation): List<Chapter>

    /**
     * 搜索
     */
    fun search(keywords: String): List<HotBook>?

    /**
     * 解析章节
     */
    fun loadChapter(chapter: Chapter): Chapter

}