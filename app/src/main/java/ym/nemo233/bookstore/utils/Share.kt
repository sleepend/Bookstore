package ym.nemo233.bookstore.utils

import android.content.Context
import ym.nemo233.bookstore.basic.MyApp
import java.io.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Class Share.
 * Created by Halcyon on 2017/9/19 0019.
 * E-mail: sleepend@foxmail.com
 * Description:
 *
 */
class Share<T>(val name: String, val default: T) : ReadWriteProperty<Any?, T> {
    private val prefs by lazy {
        MyApp.instance()
            .getSharedPreferences("config", Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return with(prefs) {
            val res: Any = when (default) {
                is Long -> getLong(name, default)
                is String -> getString(name, default)
                is Int -> getInt(name, default)
                is Boolean -> getBoolean(name, default)
                is Float -> getFloat(name, default)
                else -> deSerialization(getString(name, serialize(default)))
            }
            res as T
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        with(prefs.edit()) {
            when (value) {
                is Long -> putLong(name, value)
                is String -> putString(name, value)
                is Int -> putInt(name, value)
                is Boolean -> putBoolean(name, value)
                is Float -> putFloat(name, value)
                else -> putString(name, serialize(value))
            }.apply()
        }
    }

    /**
     * 序列化对象
     */
    @Throws(IOException::class)
    private fun <A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * 返回序列化对象
     */
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun <A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(byteArrayInputStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }

    companion object {

        const val FIRST_START_APP = "FIRST_START_APP"

        const val WEB_SITE_BEFORE_UPDATE_TIME = "WEB_SITE_BEFORE_UPDATE_TIME"
    }
}