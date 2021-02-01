package me.turkergoksu.lib.model

import android.graphics.Color

/**
 * Created by turkergoksu on 23-Jan-21.
 */
class Orbit private constructor(
        val floatingObjectList: MutableList<FloatingObject>?,
        val outerOrbitColor: Int?,
        val outerOrbitWidth: Float?,
        val outerOrbitAnimationDuration: Int?,
        val outerOrbitPadding: Float?,
        val outerOrbitStartAngle: Double?,
        val outerOrbitAngleDistance: Double?,
        val innerOrbitRadius: Float?,
        val innerOrbitColor: Int?,
        val innerOrbitWidth: Float?,
        val innerOrbitAnimationDuration: Int?,
        val innerOrbitStartAngle: Double?,
        val innerOrbitAngleDistance: Double?
) {

    data class Builder(
            var floatingObjectList: MutableList<FloatingObject>? = null,
            var outerOrbitColor: Int = Color.LTGRAY,
            var outerOrbitWidth: Float = 2f,
            var outerOrbitAnimationDuration: Int = 60000,
            var outerOrbitPadding: Float = 40f,
            var outerOrbitStartAngle: Double = 70.0,
            var outerOrbitAngleDistance: Double = 90.0,
            var innerOrbitRadius: Float = 90f,
            var innerOrbitColor: Int = Color.parseColor("#f8f4fe"),
            var innerOrbitWidth: Float = 40f,
            var innerOrbitAnimationDuration: Int = 30000,
            var innerOrbitStartAngle: Double = 20.0,
            var innerOrbitAngleDistance: Double = 120.0
    ) {

        fun setFloatingObjectList(list: MutableList<FloatingObject>) =
                apply { floatingObjectList = list }

        fun setOuterOrbitColor(color: Int) = apply { outerOrbitColor = color }
        fun setOuterOrbitWidth(width: Float) = apply { outerOrbitWidth = width }
        fun setOuterOrbitAnimDuration(duration: Int) = apply { outerOrbitAnimationDuration = duration }
        fun setOuterOrbitPadding(padding: Float) = apply { outerOrbitPadding = padding }
        fun setOuterOrbitStartAngle(angle: Double) = apply { outerOrbitStartAngle = angle }
        fun setOuterOrbitAngleDistance(distance: Double) = apply { outerOrbitAngleDistance = distance }
        fun setInnerOrbitRadius(radius: Float) = apply { innerOrbitRadius = radius }
        fun setInnerOrbitColor(color: Int) = apply { innerOrbitColor = color }
        fun setInnerOrbitWidth(width: Float) = apply { innerOrbitWidth = width }
        fun setInnerOrbitAnimDuration(duration: Int) = apply { innerOrbitAnimationDuration = duration }
        fun setInnerOrbitStartAngle(angle: Double) = apply { innerOrbitStartAngle = angle }
        fun setInnerOrbitAngleDistance(distance: Double) = apply { innerOrbitAngleDistance = distance }
        fun build() = Orbit(
                floatingObjectList,
                outerOrbitColor,
                outerOrbitWidth,
                outerOrbitAnimationDuration,
                outerOrbitPadding,
                outerOrbitStartAngle,
                outerOrbitAngleDistance,
                innerOrbitRadius,
                innerOrbitColor,
                innerOrbitWidth,
                innerOrbitAnimationDuration,
                innerOrbitStartAngle,
                innerOrbitAngleDistance
        )
    }
}