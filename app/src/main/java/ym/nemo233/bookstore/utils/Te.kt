package ym.nemo233.bookstore.utils

/**
 * Created by 打印显示用,对项目无作用 on 2017/6/19 0019.
 */

object Te {

    @JvmStatic
    fun toString(o: Any): String {
        val clazz = o.javaClass
        val sb = StringBuffer(clazz.simpleName)
        try {
            sb.append(" [\n")
            val fields = clazz.declaredFields
            for (field in fields) {
                field.isAccessible = true
                sb.append("\t\t" + field.name)
                sb.append(" : ")
                sb.append(field.get(o))
                sb.append(",\n")
            }
            sb.deleteCharAt(sb.lastIndexOf(","))
            sb.append("]")
        } catch (e: Exception) {
        }
        return sb.toString()
    }

    @JvmStatic
    fun toString2(o: Any): String {
        val clazz = o.javaClass
        val sb = StringBuffer(clazz.simpleName)
        try {
            sb.append(" [ ")
            val fields = clazz.declaredFields
            for (field in fields) {
                field.isAccessible = true
                sb.append(field.name)
                sb.append(" : ")
                sb.append(field.get(o))
                sb.append(",")
            }
            sb.deleteCharAt(sb.lastIndexOf(","))
            sb.append("]")
        } catch (e: Exception) {
        }
        return sb.toString()
    }
}
