package me.turkergoksu.socialorbitlayout

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path

/**
 * Created by turkergoksu on 23-Jan-21.
 */
data class Orbit(
    var floatingObjects: MutableList<FloatingObject>? = null,
) {

    // Attributes
    // Outer orbit
    var outerOrbitColor: Int = Color.LTGRAY
    var outerOrbitWidth: Float = 5f
    var outerOrbitAnimationDuration: Int = 60000 // 60 seconds
    var outerOrbitPadding: Float = 100f

    // Inner orbit
    var innerOrbitColor: Int = Color.parseColor("#f8f4fe")
    var innerOrbitWidth: Float = 120f
    var innerOrbitAnimationDuration: Int = 30000 // 30 seconds

}