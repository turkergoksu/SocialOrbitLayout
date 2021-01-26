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

    private var rectF: RectF? = null
    private var floatingObjectBorderWith = 10f // FIXME: 25-Jan-21 maybe add to FloatingObject.kt

    private var floatingObject: FloatingObject? = null

    var myW = 0
    var myH = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // TODO: 26-Jan-21 https://stackoverflow.com/a/12267248/6771753
        val desiredWidth = 120 // (50 + 10) + (50 + 10)
        val desiredHeight = 120

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //Measure Width
        myW = if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            min(desiredWidth, widthSize)
        } else {
            //Be whatever you want
            desiredWidth
        }

        //Measure Height
        myH = if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            min(desiredHeight, heightSize)
        } else {
            //Be whatever you want
            desiredHeight
        }

        //MUST CALL THIS
        setMeasuredDimension(myW, myH)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rectF = RectF(
            0f,
            0f,
            myW.coerceAtMost(myH).toFloat(),
            myW.coerceAtMost(myH).toFloat()
        )

        outlineProvider = FloatingObjectOutline(rectF!!.toRect(), 60f)
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
        canvas?.drawCircle(
            x + floatingObjectBorderWith - 150,
            y + floatingObjectBorderWith - 150,
            floatingObject.bitmap.width / 2f + floatingObjectBorderWith,
            backgroundPaint
        )

        // Draw image
        canvas?.drawBitmap(
            floatingObject.bitmap,
            x - floatingObject.bitmap.width / 2f + floatingObjectBorderWith - 150,
            y - floatingObject.bitmap.width / 2f + floatingObjectBorderWith - 150,
            Paint()
        )
    }

    fun setFloatingObject(floatingObject: FloatingObject) {
        floatingObject.convertToCircularBitmap()
        this.floatingObject = floatingObject
        invalidate()
    }

    // TODO: 26-Jan-21 https://stackoverflow.com/a/27497988/6771753
    private class FloatingObjectOutline(val rect: Rect, val radius: Float) : ViewOutlineProvider() {

        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(rect, radius)
        }
    }
}