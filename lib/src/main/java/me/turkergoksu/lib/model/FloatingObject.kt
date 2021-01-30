package me.turkergoksu.lib.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path

/**
 * Created by turkergoksu on 23-Jan-21.
 */
data class FloatingObject(
    val backgroundColor: Int,
    var bitmap: Bitmap,
    val borderWidth: Float = 2f,
    val size: Float = 40f,
    val elevation: Float = 10f,
    val location: FloatingObjectLocation = FloatingObjectLocation.OUTER
)

enum class FloatingObjectLocation {
    OUTER,
    INNER,
    CENTER
}