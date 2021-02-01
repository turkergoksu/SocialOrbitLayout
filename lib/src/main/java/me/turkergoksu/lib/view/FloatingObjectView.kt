package me.turkergoksu.lib.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.graphics.toRect
import me.turkergoksu.lib.toPx
import me.turkergoksu.lib.model.FloatingImage
import me.turkergoksu.lib.model.FloatingObjectLocation
import kotlin.math.min

/**
 * Created by turkergoksu on 26-Jan-21.
 */
class FloatingObjectView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Member variables
    private var floatingImage: FloatingImage? = null

    // Drawing
    private var rectF: RectF? = null

    private var floatingObjMeasuredWidth: Int = 100
    private var floatingObjMeasuredHeight: Int = 100

    private var radius: Float? = null

    private fun init() {
        // Set radius
        floatingImage?.let {
            radius = (it.bitmap.width / 2f + it.borderWidth.toPx(resources.displayMetrics))
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // TODO: 26-Jan-21 https://stackoverflow.com/a/12267248/6771753
        if (floatingImage != null) {
            val desiredWidth =
                ((floatingImage!!.size / 2 + floatingImage!!.borderWidth) * 2).toPx(resources.displayMetrics)
                    .toInt()
            val desiredHeight =
                ((floatingImage!!.size / 2 + floatingImage!!.borderWidth) * 2).toPx(resources.displayMetrics)
                    .toInt()

            val widthMode = MeasureSpec.getMode(widthMeasureSpec)
            val widthSize = MeasureSpec.getSize(widthMeasureSpec)
            val heightMode = MeasureSpec.getMode(heightMeasureSpec)
            val heightSize = MeasureSpec.getSize(heightMeasureSpec)

            //Measure Width
            floatingObjMeasuredWidth = when (widthMode) {
                MeasureSpec.EXACTLY -> widthSize
                MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
                else -> desiredWidth
            }

            //Measure Height
            floatingObjMeasuredHeight = when (heightMode) {
                MeasureSpec.EXACTLY -> heightSize
                MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
                else -> desiredHeight
            }

            //MUST CALL THIS
            setMeasuredDimension(floatingObjMeasuredWidth, floatingObjMeasuredHeight)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rectF = RectF(
            0f,
            0f,
            floatingObjMeasuredWidth.coerceAtMost(floatingObjMeasuredHeight).toFloat(),
            floatingObjMeasuredWidth.coerceAtMost(floatingObjMeasuredHeight).toFloat()
        )

        // Assigning custom outline to enable z axis which is needed for shadow effect
        outlineProvider = radius?.let { FloatingObjectOutline(rectF!!.toRect(), it) }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        floatingImage?.let { drawFloatingObject(canvas, it) }
    }

    private fun drawFloatingObject(
            canvas: Canvas?,
            floatingImage: FloatingImage
    ) {
        val backgroundPaint = Paint()
        backgroundPaint.color = floatingImage.backgroundColor
        radius?.let {
            canvas?.drawCircle(
                (floatingImage.borderWidth.toPx(resources.displayMetrics) + floatingImage.bitmap.width / 2),
                (floatingImage.borderWidth.toPx(resources.displayMetrics) + floatingImage.bitmap.width / 2),
                it,
                backgroundPaint
            )
        }

        // Draw image
        canvas?.drawBitmap(
            floatingImage.bitmap,
            (radius!! - floatingImage.bitmap.width / 2),
            (radius!! - floatingImage.bitmap.width / 2),
            Paint()
        )
    }

    fun setFloatingObject(floatingImage: FloatingImage) {
        this.floatingImage = floatingImage
        init()
        invalidate()
    }

    fun getFloatingObjectLocation(): FloatingObjectLocation =
            floatingImage?.location!!

    // TODO: 26-Jan-21 https://stackoverflow.com/a/27497988/6771753
    private class FloatingObjectOutline(val rect: Rect, val radius: Float) : ViewOutlineProvider() {

        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(rect, radius)
        }
    }
}