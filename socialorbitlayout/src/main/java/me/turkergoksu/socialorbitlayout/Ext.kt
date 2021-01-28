package me.turkergoksu.socialorbitlayout

import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * Created by turkergoksu on 28-Jan-21.
 */

/**
 * Converting any dp value to px value with given display metrics.
 */
fun Float.dpToPx(displayMetrics: DisplayMetrics): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        displayMetrics
    )
}