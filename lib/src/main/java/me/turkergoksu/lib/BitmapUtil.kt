package me.turkergoksu.lib

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Path
import android.util.DisplayMetrics

/**
 * Created by turkergoksu on 27-Jan-21.
 */
object BitmapUtil {

    /**
     * Get scaled bitmap with given size and borderWidth
     */
    fun getScaledBitmap(
        bitmap: Bitmap,
        size: Float,
        borderWidth: Float,
        displayMetrics: DisplayMetrics
    ): Bitmap =
        Bitmap.createScaledBitmap(
            bitmap,
            (size - borderWidth).toPx(displayMetrics).toInt(),
            (size - borderWidth).toPx(displayMetrics).toInt(),
            false
        )


    /**
     * Get circular cropped image for given image.
     */
    fun getCircularCroppedBitmap(bitmap: Bitmap): Bitmap {
        // TODO: 23-Jan-21  : https://stackoverflow.com/a/15489830/6771753
        val width: Int = bitmap.width
        val height: Int = bitmap.height
        val outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val path = Path()
        path.addCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            width.coerceAtMost(height / 2).toFloat(),
            Path.Direction.CCW
        )

        val canvas = Canvas(outputBitmap)
        canvas.clipPath(path)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        return outputBitmap
    }
}