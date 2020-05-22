package ym.nemo233.bookstore

object Test {
    @JvmStatic
    fun main(args:Array<String>){
        val list = arrayOf(1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9)
        println(list.take(12))
        println(list.takeLast(list.size-5))
    }
}