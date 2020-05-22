package ym.nemo233.bookstore.parse

import ym.nemo233.bookstore.sqlite.BookcaseClassifyCache
import ym.nemo233.bookstore.sqlite.BooksInformation
import ym.nemo233.bookstore.sqlite.Chapter

interface SiteParser {
    /**
     * 加载书城分类
     */
    fun loadBookcaseClassify(): List<BookcaseClassifyCache>?

    /**
     * 解析分类获得列表
     */
    fun loadBooksByClassify(classifyCache: BookcaseClassifyCache): List<BooksInformation>

    /**
     * 加载详情&最新章节
     */
    fun loadNewestChapter(booksInformation: BooksInformation, chapterSize: Int): ArrayList<Chapter>

    /**
     * 缓存目录
     * @return 最新章节
     */
    fun cacheChapters(booksInformation: BooksInformation): Boolean

    /**
     * 解析章节
     */
    fun loadChapter(chapter: Chapter): Chapter

    fun searchBook(bookName: String): List<BooksInformation>?

}