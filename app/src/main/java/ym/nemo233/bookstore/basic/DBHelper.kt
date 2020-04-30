package ym.nemo233.bookstore.basic

import ym.nemo233.bookstore.sqlite.*

object DBHelper {
    private var firstStartApp by Share(Share.FIRST_START_APP, true)
    //操作对象
    private val daoSession by lazy { MyApp.instance().daoMaster.newSession() }
    private val daoPopularBooks by lazy { daoSession.popularBooksDao }
    private val daoWebsiteSource by lazy { daoSession.websiteSourceDao }

    private val booksSiteDao by lazy { daoSession.booksSiteDao }

    /**
     * 加载默认网站
     */
    fun loadDefaultWebsite(): WebsiteSource {
        val data = daoWebsiteSource.loadAll()
        return if (data.isEmpty()) {
            val temp = ArrayList<WebsiteSource>()
            temp.add(WebsiteSource(null, "番茄小说", "http://www.fqxs.org", true))
            daoWebsiteSource.insertInTx(temp)
            temp[0]
        } else {
            data.singleOrNull { it.selected } ?: data[0]
        }
    }

    /**
     * 根据网站站点获取推荐信息
     */
    fun getPopBookByWebsite(website: String): List<PopularBooks> {
        return daoPopularBooks.queryBuilder().where(PopularBooksDao.Properties.Website.eq(website))
            .list()
    }

    fun checkPopularBooksIsEmpty(website: String): Boolean {
        return daoPopularBooks.queryBuilder().where(PopularBooksDao.Properties.Website.eq(website))
            .count() == 0L
    }

    /**
     * 保存站点热门推荐
     */
    fun savePopularBooks(data: ArrayList<PopularBooks>) {
        daoPopularBooks.insertInTx(data)
    }

    /**
     * 分类加载推荐
     */
    fun loadPopularBooks(): LinkedHashMap<String, ArrayList<PopularBooks>> {
        val data = LinkedHashMap<String, ArrayList<PopularBooks>>()
        daoPopularBooks.loadAll().forEach { pb ->
            if (!data.containsKey(pb.typeName)) {
                data[pb.typeName] = ArrayList()
            }
            data[pb.typeName]?.add(pb)
        }
        return data
    }

    fun loadWebsites(): List<WebsiteSource> = daoWebsiteSource.loadAll()

    /**
     * 初始化数据库
     */
    fun initDatabases(firstStartApp: Boolean) {
        if (firstStartApp) {
            val data = BooksSite(0, "番茄小说", "http://www.fqxs.org", 100, true)
            booksSiteDao.insertInTx(data)
        }
        this.firstStartApp = false
    }

    fun loadDefaultSite(): BooksSite? {
        return booksSiteDao.queryBuilder().where(BooksSiteDao.Properties.DefaultSite.eq(true))
            .unique()
    }

}