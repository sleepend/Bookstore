package ym.nemo233.bookstore.basic

import ym.nemo233.bookstore.sqlite.*
import ym.nemo233.bookstore.utils.Encryption

object DBHelper {
    //操作对象
    private val daoSession by lazy { MyApp.instance().daoMaster.newSession() }
    private val webSiteDao by lazy { daoSession.webSiteDao }

    private val bookInformationDao by lazy { daoSession.bookInformationDao }
    private val chapterDao by lazy { daoSession.chapterDao }
    private val historyQueryDao by lazy { daoSession.historyQueryDao }

    /**
     * 加载默认站点
     */
    fun loadDefaultSite(): WebSite {
        val data = webSiteDao.loadAll().singleOrNull() { it.isDefault == 1 }
        return if (data == null) {
            val webSite = WebSite(null, "番茄小说", "http://www.fqxs.org", "UTF-8", 99, 1, "", -1)
            webSiteDao.insertInTx(webSite)
            webSite
        } else data
    }

    fun loadBookSite(): MutableList<WebSite>? {
        return webSiteDao.loadAll()
    }

    fun hasInsertBookcase(bookInformation: BookInformation): Boolean {
        val identityCode =
            Encryption.Bit32(bookInformation.name + bookInformation.auth + bookInformation.sourceUrl)
        return bookInformationDao.queryBuilder().where(
            BookInformationDao.Properties.IdentifyCode.eq(
                identityCode
            )
        ).unique() != null
    }

    fun loadChapterByBook(book: BookInformation): Chapter {
        return chapterDao.queryBuilder().where(
            ChapterDao.Properties.Index.eq(book.currentChapter),
            ChapterDao.Properties.BiId.eq(book.id)
        ).limit(1).offset(0).unique()
    }

    fun loadBookcase(): List<BookInformation> {
        return bookInformationDao.loadAll()
    }

    fun loadWebsites(webSiteId: Long): List<WebSite>? {
        return webSiteDao.queryBuilder().where(WebSiteDao.Properties.Parent.eq(webSiteId)).list()
    }

    /**
     * 添加到书架
     */
    fun insertBook(bookInformation: BookInformation): BookInformation? {
        try {
            bookInformation.identifyCode =
                Encryption.Bit32(bookInformation.name + bookInformation.auth + bookInformation.sourceUrl)
            bookInformationDao.insertInTx(bookInformation)
            return bookInformationDao.queryBuilder()
                .where(BookInformationDao.Properties.IdentifyCode.eq(bookInformation.identifyCode))
                .unique()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun initializationConfiguration() {
        if (webSiteDao.loadAll().isNotEmpty()) {
            return
        }
        webSiteDao.insertInTx(arrayListOf<WebSite>().apply {
            //add(WebSite(null, "起点推荐", "https://m.qidian.com/", "UTF-8", 99, 1, "", -1))
            add(WebSite(null, "番茄推荐", "http://m.fqxs.org", "UTF-8", 99, 1, "/modules/article/search.php", -1))
            add(WebSite(null, "完本推荐", "http://www.fqxs.org", "UTF-8", 99, 1, "", -1))
        })
        historyQueryDao.insertInTx(arrayListOf<HistoryQuery>().apply {
            add(HistoryQuery())
        })
    }

    /**
     * 加载搜索关键字
     */
    fun loadSearchKeywords():MutableList<HistoryQuery> {
        return historyQueryDao.queryBuilder()
            .orderAsc(HistoryQueryDao.Properties.Stamp,HistoryQueryDao.Properties.Sort)
            .distinct()
            .list()
    }


}