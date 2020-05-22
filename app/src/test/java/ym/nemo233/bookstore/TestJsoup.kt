package ym.nemo233.bookstore

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import ym.nemo233.bookstore.utils.Te
import java.net.URL

object TestJsoup {
    @JvmStatic
    fun main(args: Array<String>) {
        val baseUrl = "http://www.fqxs.org"
        val home = Jsoup.parse(URL(baseUrl).openStream(), "UTF-8", baseUrl)
        val nav = loadNavigation(home.body())
        //推荐列表
        val recommends = loadRecommend(baseUrl, nav)//推荐目录
////        //打开目录
        val chapters = loadBookCatalogue(baseUrl, recommends.values.elementAt(1)[3])//测试用:第2分类,第四本书
//        //打开指定章节
//        val chapter = chapters[149]
//        openChapter(chapter)
//        println("${chapter.tag} ${chapter.url}\n ${chapter.content}")
//        //搜索
//        searchBook(baseUrl, "仙").forEach {
//            println("${it.name} | ${it.auth} \n${it.url}")
//        }
    }

    /**
     * 搜索
     */
    private fun searchBook(baseUrl: String, searchKey: String): List<SearchResult> {
        val url = "$baseUrl/modules/article/search.php"
        val headers = HashMap<String, String>()
        headers["Accept"] =
            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
        headers["User-Agent"] =
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3"
        headers["Connection"] = "close"
        headers["Cache-Control"] = "no-cache"
        headers["Accept-Encoding"] = "gzip, deflate, br"
        headers["Accept-Language"] = "zh-CN,zh;q=0.9"
        headers["Cache-Control"] = "max-age=0"
        headers["Cookie"] =
            "Hm_lvt_48243a1cfad2f602e12a3cb7386cace7=1583462211,1583465166,1583475933; Hm_lpvt_48243a1cfad2f602e12a3cb7386cace7=1583475933"
        val doc = Jsoup.connect(url).headers(headers)
            .postDataCharset("GBK")
            .method(Connection.Method.POST)
            .data("keyword", searchKey)
            .execute().parse()
        val body = doc.body().getElementById("main")
        val lis = body.select("li")
        if (lis.size > 1) {
            lis.removeAt(0)//删除标题行
            return lis.map { li ->
                val href = li.select("span[class=s2]").select("a[href]")
                val name = href.text()
                val url = href.attr("href")
                val auth = li.select("span[class=s4]").text()
                SearchResult(name, url, auth)
            }
        }
        return ArrayList()
    }

    /**
     * 打开/载入内容
     */
    private fun openChapter(chapter: Chapter) {
        val body = Jsoup.parse(URL(chapter.url).openStream(), "GBK", chapter.url).body()
        val content =
            body.getElementById("content").html().replace("<p>", "").replace("</p>", "").trimEnd()
        chapter.content = content
    }

    /**
     * 加载书籍目录
     */
    private fun loadBookCatalogue(baseUrl: String, bookInfo: Temp1): ArrayList<Chapter> {
        val doc = Jsoup.parse(URL(bookInfo.url).openStream(), "GBK", bookInfo.url)
        val body = doc.body()
        val data = ArrayList<Chapter>()
        body.select("dd").map { dd ->
            val href = dd.select("a[href]")
            val tag = href.text()
            val url = href.attr("href")
            if (url.startsWith("http")) {
                data.add(Chapter(tag, url))
            } else {
                data.add(Chapter(tag, baseUrl + url))
            }
        }
        return data
    }

    /**
     * 加载推荐目录
     */
    private fun loadRecommend(
        baseUrl: String,
        hashMap: HashMap<String, String>
    ): HashMap<String, ArrayList<Temp1>> {
        val data = HashMap<String, ArrayList<Temp1>>()
        hashMap.forEach { (key, value) ->
            val url = baseUrl + value
            val lst = ArrayList<Temp1>()
            val hot = Jsoup.parse(URL(url).openStream(), "GBK", url)
            hot.getElementById("hotcontent").select("div[class=item]").forEach { item ->
                val imgTag = item.getElementsByTag("img")
                val img = imgTag.attr("src")
                println("tag:img:$imgTag\n$img")
                val dt = item.getElementsByTag("dt")
                val auth = dt.select("span").text()
                val href = dt.select("a[href]")
                val name = href.text()
                val url = baseUrl + href.attr("href")
                val instr = item.getElementsByTag("dd").text()
                val temp1 = Temp1(baseUrl + img, name, auth, url, instr)
                println(Te.toString(temp1))
                lst.add(temp1)
            }
            data[key] = lst
        }
        return data
    }

    /**
     * 加载导航页
     */
    private fun loadNavigation(body: Element): HashMap<String, String> {
        val map = LinkedHashMap<String, String>()
        val lis = body.select("div[class=nav]").select("li")
        if (lis != null) {
            for (i in 1..lis.size - 3) {
                val li = lis[i].select("a[href]")
                map[li.text()] = li.attr("href")
                print("[log-nav]${li.text()} ${map[li.text()]}")
            }
        }
        return map
    }

    data class Temp1(
        val image: String,
        val name: String,
        val auth: String,
        val url: String,
        val instr: String
    )

    data class Chapter(val tag: String, val url: String, var content: String = "")
    data class SearchResult(val name: String, val url: String, val auth: String)
}