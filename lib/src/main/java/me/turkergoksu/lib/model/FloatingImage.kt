package me.turkergoksu.lib.model

import android.graphics.Bitmap
import android.graphics.Color

/**
 * Created by turkergoksu on 23-Jan-21.
 */
class FloatingImage private constructor(
        var bitmap: Bitmap,
        val backgroundColor: Int,
        val borderWidth: Float,
        val size: Float,
        val elevation: Float,
        val location: FloatingObjectLocation
) {

    data class Builder(
            private var bitmap: Bitmap? = null,
            override var backgroundColor: Int = Color.WHITE,
            override var borderWidth: Float = 2f,
            override var size: Float = 40f,
            override var elevation: Float = 10f,
            override var location: FloatingObjectLocation = FloatingObjectLocation.OUTER
    ) : FloatingObjectBuilder<Builder, FloatingImage> {

        fun setBitmap(bitmap: Bitmap): Builder =
                apply { this.bitmap = bitmap }

        override fun setBackgroundColor(backgroundColor: Int): Builder =
                apply { this.backgroundColor = backgroundColor }

        override fun setBorderWidth(borderWidth: Float): Builder =
                apply { this.borderWidth = borderWidth }

        override fun setSize(size: Float): Builder =
                apply { this.size = size }

        override fun setElevation(elevation: Float): Builder =
                apply { this.elevation = elevation }

        override fun setLocation(location: FloatingObjectLocation): Builder =
                apply { this.location = location }

        // TODO: 02-Feb-21 Sending null pointer exception if bitmap is null. Maybe add custom exception
        override fun build(): FloatingImage = FloatingImage(
                bitmap!!,
                backgroundColor,
                borderWidth,
                size,
                elevation,
                location
        )
    }
}