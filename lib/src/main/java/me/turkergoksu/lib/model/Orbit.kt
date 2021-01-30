package me.turkergoksu.lib.model

import android.graphics.Color

/**
 * Created by turkergoksu on 23-Jan-21.
 */
data class Orbit(
    var floatingObjects: MutableList<FloatingObject>? = null,
    var outerOrbitColor: Int = Color.LTGRAY,
    var outerOrbitWidth: Float = 2f,
    var outerOrbitAnimationDuration: Int = 60000,
    var outerOrbitPadding: Float = 40f,
    var innerOrbitRadius: Float = 90f,
    var innerOrbitColor: Int = Color.parseColor("#f8f4fe"),
    var innerOrbitWidth: Float = 40f,
    var innerOrbitAnimationDuration: Int = 30000
//    var centerFloatingObject: FloatingObject? = null
)