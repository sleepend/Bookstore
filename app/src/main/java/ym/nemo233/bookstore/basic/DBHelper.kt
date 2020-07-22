package ym.nemo233.bookstore.basic

import ym.nemo233.bookstore.sqlite.*
import ym.nemo233.framework.utils.L

object DBHelper {
    //操作对象
    private val daoSession by lazy { MyApp.instance().daoMaster.newSession() }
    private val webSiteDao by lazy { daoSession.webSiteDao }

    private val bookInformationDao by lazy { daoSession.bookInformationDao }
    private val chapterDao by lazy { daoSession.chapterDao }
    private val historyQueryDao by lazy { daoSession.historyQueryDao }

    private val hotBookDao by lazy { daoSession.hotBookDao }

    /**
     * 加载默认站点
     */
    fun loadDefaultSite(): WebSite {
        val data = webSiteDao.loadAll().singleOrNull() { it.isDefault == 1 }
        return if (data == null) {
            val webSite = WebSite(null, "番茄小说", "http://www.fqxs.org", "UTF-8", 99, 1, "", "", -1)
            webSiteDao.insertInTx(webSite)
            webSite
        } else data
    }

    fun loadBookSite(): MutableList<WebSite>? {
        return webSiteDao.loadAll()
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

    fun loadWebsites(): List<WebSite> = webSiteDao.loadAll()


    fun initializationConfiguration() {
        if (webSiteDao.loadAll().isNotEmpty()) {
            return
        }
        webSiteDao.insertInTx(arrayListOf<WebSite>().apply {
            //add(WebSite(null, "起点推荐", "https://m.qidian.com/", "UTF-8", 99, 1, "", -1))
            add(
                WebSite(
                    null,
                    "番茄推荐",
                    "http://m.fqxs.org",
                    "UTF-8",
                    99,
                    1,
                    "/modules/article/search.php",
                    "/quanben",
                    -1
                )
            )
//            add(WebSite(null, "完本推荐", "http://www.fqxs.org", "UTF-8", 99, 1, "", "", -1))
        })
//        historyQueryDao.insertInTx(arrayListOf<HistoryQuery>().apply {
//            add(HistoryQuery())
//        })
    }

    /**
     * 加载搜索关键字
     */
    fun loadSearchKeywords(): MutableList<HistoryQuery> {
        return historyQueryDao.queryBuilder()
            .orderAsc(HistoryQueryDao.Properties.Stamp)
            .distinct()
            .list()
    }

    /**
     * 取出缓存
     */
    fun loadLocalHotBooks(webSite: WebSite): List<HotBook>? {
        val beforeCacheTime = System.currentTimeMillis() - 24 * 60 * 60 * 1000 //1天前
        return hotBookDao.queryBuilder().where(
            HotBookDao.Properties.SiteTag.eq(webSite.name),
            HotBookDao.Properties.Stamp.gt(beforeCacheTime)
        ).list()
    }

    /**
     * 保存到本地
     * 先删除之前,后添加
     */
    fun saveHotBooks(webSite: WebSite, data: List<HotBook>) {
        val beforeCacheTime = System.currentTimeMillis() - 48 * 60 * 60 * 1000 //2天前
        hotBookDao.queryBuilder().where(
            HotBookDao.Properties.SiteTag.eq(webSite.name),
            HotBookDao.Properties.Stamp.lt(beforeCacheTime)
        ).buildDelete().executeDeleteWithoutDetachingEntities()
        hotBookDao.insertInTx(data)
        //
        L.i("[log] 删除之前数据,保存当天数据")
    }

    /**
     * 加载本地缓存记录
     */
    fun loadLocalCacheBook(hotBook: HotBook): BookInformation? {
        return bookInformationDao.queryBuilder()
            .where(
                BookInformationDao.Properties.Name.eq(hotBook.name),
                BookInformationDao.Properties.Auth.eq(hotBook.auth),
                BookInformationDao.Properties.SourceUrl.eq(hotBook.sourceUrl)
            )
            .list().firstOrNull()
    }

    fun saveBook(bookInformation: BookInformation) {
        bookInformationDao.insertOrReplaceInTx(bookInformation)
    }

    /**
     * 最新15节
     */
    fun loadLocalChapterByBook(cache: BookInformation): MutableList<Chapter>? {
        return chapterDao.queryBuilder().where(ChapterDao.Properties.BiId.eq(cache.id))
            .orderDesc(ChapterDao.Properties.Id).limit(15).list()
    }

    /**
     * 无id查询本地书籍
     */
    fun findBookByNotId(bookInformation: BookInformation): BookInformation? {
        return bookInformationDao.queryBuilder().where(
            BookInformationDao.Properties.Name.eq(bookInformation.name),
            BookInformationDao.Properties.Auth.eq(bookInformation.auth),
            BookInformationDao.Properties.SourceUrl.eq(bookInformation.sourceUrl)
        ).list().firstOrNull()
    }

    /**
     * 保存所有章节
     */
    fun saveChapters(chapters: List<Chapter>) {
        chapterDao.insertInTx(chapters)
    }

    /**
     * 保存历史查询
     */
    fun saveHistoryQuery(keywords: String) {
        val first =
            historyQueryDao.queryBuilder().where(HistoryQueryDao.Properties.SearchKey.eq(keywords))
                .list().firstOrNull()
        if (first == null) {//添加 or 更新时间戳
            historyQueryDao.saveInTx(HistoryQuery(null, keywords, System.currentTimeMillis()))
        } else {
            first.stamp = System.currentTimeMillis()
            historyQueryDao.update(first)
        }
    }

    fun loadBooks(): MutableList<BookInformation> {
        return bookInformationDao.loadAll()
    }


}