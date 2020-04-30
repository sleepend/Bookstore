package ym.nemo233.bookstore.basic

import android.app.Activity
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


fun Activity.toast(msg: Int) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Activity.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//fun Fragment.toast(msg: Int) = ToastUtil.show(msg)
fun Fragment.toast(msg: Int) = android.widget.Toast.makeText(activity, msg, android.widget.Toast.LENGTH_SHORT).show()

fun Fragment.toast(msg: String) = android.widget.Toast.makeText(activity, msg, android.widget.Toast.LENGTH_SHORT).show()

/**
 * ImageView加载图片
 */
fun ImageView.loadUrl(url: String,options: RequestOptions = RequestOptions()
    .diskCacheStrategy(DiskCacheStrategy.ALL)) {
    Glide.with(context)
        .load(url)
        .dontAnimate()
        .centerCrop()
        .apply(options)
        .into(this)
}