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
    val resolution: Int = 100 // FIXME: 26-Jan-21 put a proper variable name
) {
    fun convertToCircularBitmap() {
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, resolution, resolution, false)
        val circularBitmap = getCircularCroppedBitmap(scaledBitmap)
        bitmap = circularBitmap
    }

    // TODO: 23-Jan-21 You should move this func to BitmapUtil class or smt..
    private fun getCircularCroppedBitmap(bitmap: Bitmap): Bitmap {
        // TODO: 23-Jan-21  : https://stackoverflow.com/a/15489830/6771753
        val width: Int = bitmap.getWidth()
        val height: Int = bitmap.getHeight()
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