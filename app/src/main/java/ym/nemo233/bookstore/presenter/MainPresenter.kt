package ym.nemo233.bookstore.presenter

import org.jsoup.Jsoup
import ym.nemo233.bookstore.basic.DBHelper
import ym.nemo233.bookstore.basic.MainView
import ym.nemo233.bookstore.sqlite.PopularBooks
import ym.nemo233.bookstore.utils.Te
import ym.nemo233.framework.mvp.BasePresenter
import ym.nemo233.framework.utils.L
import java.net.URL

class MainPresenter(view: MainView) : BasePresenter<MainView>() {

    init {
        attachView(view)
    }

    /**
     * 加载默认分类
     */
    fun loadDefaultPopularBooks() {
        val website = DBHelper.loadDefaultWebsite()
        Thread {
            if (DBHelper.checkPopularBooksIsEmpty(website.name)) {//为空,表示需要添加推荐到数据库中
                val home = Jsoup.parse(URL(website.website).openStream(), "GBK", website.website)
                val body = home.body()
                //获得导航分类
                val map = LinkedHashMap<String, String>()
                val lis = body.select("div[class=nav]").select("li")
                if (lis != null) {
                    for (i in 1..lis.size - 3) {
                        val li = lis[i].select("a[href]")
                        map[li.text()] = li.attr("href")
                    }
                }
                //获取各分类的推荐
                map.keys.forEach { key ->
                    val url = website.website + map[key]
                    val lst = ArrayList<PopularBooks>()
                    Jsoup.parse(URL(url).openStream(), "GBK", url).getElementById("hotcontent")
                        .select("div[class=item]").forEachIndexed { index, item ->
                            val imgTag = item.getElementsByTag("img")
                            val img = imgTag.attr("src")
                            val dt = item.getElementsByTag("dt")
                            val auth = dt.select("span").text()
                            val href = dt.select("a[href]")
                            val name = href.text()
                            val url = website.website + href.attr("href")
                            val instr = item.getElementsByTag("dd").text()
                            lst.add(
                                PopularBooks(
                                    null,
                                    website.name,
                                    index,
                                    key,
                                    website.website,
                                    website.website + map[key],
                                    name,
                                    auth,
                                    website.website + img,
                                    url,
                                    instr
                                )
                            )
                        }
                    //可以添加入库了
                    DBHelper.savePopularBooks(lst)
                }
            }
        }.start()
    }
}