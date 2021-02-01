package me.turkergoksu.lib.model

import android.graphics.Bitmap
import android.graphics.Color

/**
 * Created by turkergoksu on 23-Jan-21.
 */
data class FloatingImage(
        val backgroundColor: Int = Color.WHITE,
        var bitmap: Bitmap,
        val borderWidth: Float = 2f,
        val size: Float = 40f,
        val elevation: Float = 10f,
        val location: FloatingObjectLocation = FloatingObjectLocation.OUTER
)

// TODO: 02-Feb-21 make super class named FloatingObject
// TODO: 02-Feb-21 Create FloatingText class that extends FloatingObject

enum class FloatingObjectLocation {
    OUTER,
    INNER,
    CENTER
}