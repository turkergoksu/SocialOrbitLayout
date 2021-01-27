package me.turkergoksu.socialorbitlayout

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path

/**
 * Created by turkergoksu on 23-Jan-21.
 */
class FloatingObject(
    val backgroundColor: Int,
    var bitmap: Bitmap,
    val borderWidth: Float = 10f,
    val size: Int = 100,
    val elevation: Float = 10f,
) {

    init {
        convertToCircularBitmap()
    }

    private fun convertToCircularBitmap() {
        val scaledBitmap = getScaledBitmap()
        val circularBitmap = getCircularBitmap(scaledBitmap)
        bitmap = circularBitmap
    }

    private fun getCircularBitmap(scaledBitmap: Bitmap): Bitmap =
        BitmapUtil.getCircularCroppedBitmap(scaledBitmap)

    private fun getScaledBitmap(): Bitmap =
        Bitmap.createScaledBitmap(
            bitmap,
            (size - borderWidth.toInt()),
            size - borderWidth.toInt(),
            false
        )
}