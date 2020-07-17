package ym.nemo233.bookstore

import org.jsoup.Jsoup
import java.net.URL

object Test {
    @JvmStatic
    fun main(args: Array<String>) {
        val url = "http://m.fqxs.org/quanben/"
        val decode = "UTF-8"
        val html = Jsoup.parse(URL(url).openStream(), decode, url)
        println(html.baseUri())
        println(html.charset())
        println(html.location())
        // 推荐标签查找 body:select=bookbox:list=[tag=img]
        val notes = html.body().select("div[class=bookbox]")
        notes.forEach {
            val imgTag = it.getElementsByTag("img")
            val imgUrl = imgTag.attr("src")
            val info = it.select("div[class=bookinfo]")
            val nameTag = info.select("a")[0]
            val name = nameTag.select("a[href]").text()
            val href = nameTag.attr("href")
            val auth = it.select("div[class=author]").text()
            val updateTag = info.select("a")[1]
            val chapterName = updateTag.text()
            val chapterUrl = updateTag.attr("href")
            println("imageUrl = $imgUrl; name = $name; url = $href;\n auth=$auth; chapter=$chapterName\n chapterUrl=$chapterUrl")
        }


//        val div = html.body().select("div[data-l1=4]")
//        val lis = div.select("li[class=module-slide-li]")
//        lis.forEach {
//            println(it.text())
//            println(it.text().split(" "))
//            println(it.select("a[href]").attr("href"))
//            println(it.getElementsByTag("img").attr("src"))
//        }
    }
}