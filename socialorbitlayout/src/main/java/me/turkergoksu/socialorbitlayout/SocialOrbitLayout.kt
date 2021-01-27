package me.turkergoksu.socialorbitlayout

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Created by turkergoksu on 26-Jan-21.
 */
class SocialOrbitLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var rectF: RectF? = null
    private var paint = Paint()

    private var orbitPadding = 100f

    init {
        // TODO: 26-Jan-21  https://stackoverflow.com/a/13056400/6771753
        setWillNotDraw(false)

        // FIXME: 25-Jan-21 temp
        paint.style = Paint.Style.STROKE
        paint.color = Color.LTGRAY
        paint.strokeWidth = 5f

        // FIXME: 27-Jan-21 temporary
        for (i in 0..3) {
            val fov = FloatingObjectView(context)
            fov.setFloatingObject(
                FloatingObject(
                    Color.WHITE,
                    BitmapFactory.decodeResource(resources, R.drawable.dummy)
                )
            )
            fov.layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            fov.elevation = 10f
            addView(fov)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val centerX = (width / 2).toFloat()
        val centerY = (height / 2).toFloat()
        val orbitRadius = centerX.coerceAtMost(centerY)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childRadius = min(child.width, child.height) / 2f

            val angle = when (i) {
                0 -> 70.0
                1 -> 160.0
                2 -> 250.0
                3 -> 340.0
                else -> 0.0
            }

            child.x =
                (centerX - childRadius) - (orbitRadius - orbitPadding) * sin(Math.toRadians(angle)).toFloat()
            child.y =
                (centerY - childRadius) + (orbitRadius - orbitPadding) * cos(Math.toRadians(angle)).toFloat()
        }
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