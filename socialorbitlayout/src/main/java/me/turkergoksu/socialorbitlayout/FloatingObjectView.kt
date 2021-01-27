package me.turkergoksu.socialorbitlayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.graphics.toRect
import kotlin.math.min

/**
 * Created by turkergoksu on 26-Jan-21.
 */
class FloatingObjectView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Member variables
    private var floatingObject: FloatingObject? = null

    // Drawing
    private var rectF: RectF? = null

    private var floatingObjMeasuredWidth: Int = 100
    private var floatingObjMeasuredHeight: Int = 100

    private var radius: Float? = null

    private fun init() {
        // Set radius
        floatingObject?.let { radius = it.bitmap.width / 2f + it.borderWidth }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // TODO: 26-Jan-21 https://stackoverflow.com/a/12267248/6771753
        if (floatingObject != null) {
            val desiredWidth =
                ((floatingObject!!.size / 2 + floatingObject!!.borderWidth) * 2).toInt()
            val desiredHeight =
                ((floatingObject!!.size / 2 + floatingObject!!.borderWidth) * 2).toInt()

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

        floatingObject?.let { drawFloatingObject(canvas, it) }
    }

    private fun drawFloatingObject(
        canvas: Canvas?,
        floatingObject: FloatingObject
    ) {
        val backgroundPaint = Paint()
        backgroundPaint.color = floatingObject.backgroundColor
        radius?.let {
            canvas?.drawCircle(
                floatingObject.borderWidth + floatingObject.bitmap.width / 2,
                floatingObject.borderWidth + floatingObject.bitmap.width / 2,
                it,
                backgroundPaint
            )
        }

        // Draw image
        canvas?.drawBitmap(
            floatingObject.bitmap,
            floatingObject.borderWidth,
            floatingObject.borderWidth,
            Paint()
        )
    }

    fun setFloatingObject(floatingObject: FloatingObject) {
        this.floatingObject = floatingObject
        init()
        invalidate()
    }

    // TODO: 26-Jan-21 https://stackoverflow.com/a/27497988/6771753
    private class FloatingObjectOutline(val rect: Rect, val radius: Float) : ViewOutlineProvider() {

        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(rect, radius)
        }
    }
}