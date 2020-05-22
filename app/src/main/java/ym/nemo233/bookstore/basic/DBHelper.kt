package ym.nemo233.bookstore.basic

import ym.nemo233.bookstore.sqlite.*
import ym.nemo233.bookstore.utils.Te
import ym.nemo233.framework.utils.L

object DBHelper {
    private var firstStartApp by Share(Share.FIRST_START_APP, true)
    //操作对象
    private val daoSession by lazy { MyApp.instance().daoMaster.newSession() }
    private val daoPopularBooks by lazy { daoSession.popularBooksDao }
    private val daoWebsiteSource by lazy { daoSession.websiteSourceDao }

    private val booksSiteDao by lazy { daoSession.booksSiteDao }
    private val booksInformationDao by lazy { daoSession.booksInformationDao }
    private val chapterDao by lazy { daoSession.chapterDao }
    private val bookcaseDao by lazy { daoSession.bookcaseDao }

    /**
     * 初始化数据库
     */
    fun initDatabases(firstStartApp: Boolean) {
        if (!firstStartApp) return
        Thread {
            L.e("[log-init] 初始化数据库")
            booksSiteDao.insert(BooksSite(null, "番茄小说", "UTF-8", "http://www.fqxs.org", 100, true))
            booksSiteDao.insert(BooksSite(null, "番茄小说网", "GBK", "http://www.fqxsw.cc", 100, false))
            this.firstStartApp = false
        }.start()
    }

    fun loadDefaultSite(): BooksSite {
        booksSiteDao.loadAll().forEach {
            L.i("[log-site] ${Te.toString2(it)}")
        }
        return booksSiteDao.loadAll().first()
    }

    fun loadBookSite(): List<BooksSite>? {
        return booksSiteDao.loadAll()
    }

    fun hasInsertBookcase(booksInformation: BooksInformation): Boolean {
        val count = bookcaseDao.queryBuilder().where(
            BookcaseDao.Properties.Tag.eq(booksInformation.name + booksInformation.auth)
        ).count()
        return count > 0
    }

    fun loadBookcase(): List<Bookcase> =
        bookcaseDao.queryBuilder().orderAsc(BookcaseDao.Properties.Sort).list()

    fun loadChapterByBook(book: BooksInformation): Chapter {
        return chapterDao.queryBuilder().where(
            ChapterDao.Properties.BiId.eq(book._id)
        ).limit(1).offset(0).unique()
    }

    fun insertChapters(data: List<Chapter>) {
        chapterDao.insertInTx(data)
    }

    /**
     * 添加到书架
     */
    fun appendToBookstore(
        booksInformation: BooksInformation,
        chapter: Chapter
    ) {
        val count = bookcaseDao.count()
        bookcaseDao.insertInTx(
            Bookcase(
                null,
                booksInformation.name + booksInformation.auth,
                booksInformation.name,
                count.toInt(),
                chapter.name,
                -1, 0, 0,
                booksInformation._id
            )
        )
    }


}