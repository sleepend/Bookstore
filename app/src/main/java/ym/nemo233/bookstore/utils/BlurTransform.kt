package ym.nemo233.bookstore.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop

/**
 * 高斯模糊
 */
class BlurTransform(val context: Context, private val radius: Float = 25f) : CenterCrop() {
    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val bitmap = super.transform(pool, toTransform, outWidth, outHeight)
        return blurBitmap(bitmap, radius, (outWidth * 0.5).toInt(), (outHeight * 0.5).toInt())
    }

    private fun blurBitmap(
        bitmap: Bitmap,
        blurRadius: Float,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        //将缩小图当预渲染的图片
        val inputBitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, false)
        val outBitmap = Bitmap.createBitmap(inputBitmap)//创建一张渲染后的输出图片
        //创建renderscript内核对象
        val rs = RenderScript.create(context)
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outBitmap)
        blurScript.setRadius(blurRadius)//最大模糊度
        blurScript.setInput(tmpIn)
        blurScript.forEach(tmpOut)
        tmpOut.copyTo(outBitmap)
        return outBitmap;
    }
}