package ym.nemo233.framework

import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

open class YMApplication : Application() {

    val displayMetrics: DisplayMetrics by lazy {
        DisplayMetrics().apply {
            (getSystemService(Context.WINDOW_SERVICE) as WindowManager)
                .defaultDisplay.getMetrics(this)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Glide.get(this)
    }


    /**
     * 自定义GSON
     */
    fun gson(): Gson = GsonBuilder()
        .enableComplexMapKeySerialization()
        .serializeNulls()
        .registerTypeAdapter(String::class.java, object : TypeAdapter<String>() {
            override fun write(out: JsonWriter, value: String?) {
                if (value == null) {
                    out.value("")
                } else {
                    out.value(value)
                }
            }

            override fun read(`in`: JsonReader?): String? {
                if (`in`?.peek() == JsonToken.NULL) {
                    `in`.nextNull()
                    return null
                }
                return `in`?.nextString() ?: ""
            }
        })
        .registerTypeAdapter(Int::class.java,object : TypeAdapter<Int>(){
            override fun write(out: JsonWriter, value: Int?) {
                if (value == null) {
                    out.value(0)
                } else {
                    out.value(value)
                }
            }

            override fun read(`in`: JsonReader?): Int {
                if (`in`?.peek() == JsonToken.NULL) {
                    `in`.nextNull()
                    return 0
                }
                return `in`?.nextInt() ?: 0
            }
        })
        .registerTypeAdapter(Double::class.java,object : TypeAdapter<Number>(){
            override fun write(out: JsonWriter, value: Number?) {
                if (value == null) {
                    out.value(0.0)
                } else {
                    out.value(value)
                }
            }

            override fun read(`in`: JsonReader?): Number {
                if (`in`?.peek() == JsonToken.NULL) {
                    `in`.nextNull()
                    return 0.0
                }
                return `in`?.nextDouble() ?: 0.0
            }
        })
        //.setDateFormat(DateFormat.LONG)
        .create()
}