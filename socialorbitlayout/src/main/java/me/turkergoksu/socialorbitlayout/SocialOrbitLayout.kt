package me.turkergoksu.socialorbitlayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Created by turkergoksu on 26-Jan-21.
 */
class SocialOrbitLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var rectF: RectF? = null
    private var paint = Paint()

    private var orbitPadding = 10f

    init {
        // TODO: 26-Jan-21  https://stackoverflow.com/a/13056400/6771753
        setWillNotDraw(false)

        // FIXME: 25-Jan-21 temp
        paint.style = Paint.Style.STROKE
        paint.color = Color.LTGRAY
        paint.strokeWidth = 5f

        // FIXME: 26-Jan-21 temporary
//        val tv = TextView(context)
//        tv.setText("seseses")
//        tv.setTextSize(24f)
//        tv.layoutParams =
//            LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        tv.x = 200f
//        tv.y = 200f
//        addView(tv)

        val fov = FloatingObjectView(context)
        fov.setFloatingObject(
            FloatingObject(
                Color.RED,
                BitmapFactory.decodeResource(resources, R.drawable.dummy)
            )
        )
        fov.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        fov.x = 200f
        fov.y = 200f
        fov.z = 50f
        addView(fov)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rectF = RectF(
            0f,
            0f,
            width.coerceAtMost(height).toFloat(),
            width.coerceAtMost(height).toFloat()
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // If this code block doesn't work try in dispatchDraw
        val centerX = (measuredWidth / 2).toFloat()
        val centerY = (measuredHeight / 2).toFloat()
        val radius = centerX.coerceAtMost(centerY)

        canvas?.drawCircle(
            rectF!!.centerX(),
            rectF!!.centerY(),
            radius - orbitPadding,
            paint
        )
    }
}