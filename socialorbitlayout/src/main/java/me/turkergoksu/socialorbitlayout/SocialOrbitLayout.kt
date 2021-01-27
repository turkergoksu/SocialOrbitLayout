package me.turkergoksu.socialorbitlayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Created by turkergoksu on 26-Jan-21.
 */
class SocialOrbitLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var outerOrbitRectF: RectF? = null
    private var outerOrbitPaint = Paint()
    private var outerOrbitPadding = 100f

    private var innerOrbitPaint = Paint()
    private var innerOrbitPaintStrokeWidth = 120f
    private var innerRadius = 0f

    private var outerOrbitAnimationDuration = 60000
    private var outerOrbitCurrentAngle = 0f

    private var innerOrbitAnimationDuration = 30000
    private var innerOrbitCurrentAngle = 0f

    init {
        // TODO: 26-Jan-21  https://stackoverflow.com/a/13056400/6771753
        setWillNotDraw(false)

        // FIXME: 25-Jan-21 temp
        outerOrbitPaint.style = Paint.Style.STROKE
        outerOrbitPaint.color = Color.LTGRAY
        outerOrbitPaint.strokeWidth = 5f

        innerOrbitPaint.style = Paint.Style.STROKE
        innerOrbitPaint.color = Color.parseColor("#f8f4fe")
        innerOrbitPaint.strokeWidth = innerOrbitPaintStrokeWidth

        // FIXME: 27-Jan-21 temporary
        for (i in 0..7) {
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
        startAnimation()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        outerOrbitRectF = RectF(
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
            outerOrbitRectF!!.centerX(),
            outerOrbitRectF!!.centerY(),
            radius - outerOrbitPadding,
            outerOrbitPaint
        )

        innerRadius = radius - outerOrbitPadding - 200
        canvas?.drawCircle(
            outerOrbitRectF!!.centerX(),
            outerOrbitRectF!!.centerY(),
            innerRadius,
            innerOrbitPaint
        )

        val orbitRadius = centerX.coerceAtMost(centerY)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childRadius = min(child.width, child.height) / 2f

            if (i < 4) {
                val angle = when (i) {
                    0 -> 70.0
                    1 -> 160.0
                    2 -> 250.0
                    3 -> 340.0
                    else -> 0.0
                }

                child.x =
                    (centerX - childRadius) - (orbitRadius - outerOrbitPadding) * sin(
                        Math.toRadians(
                            angle + outerOrbitCurrentAngle
                        )
                    ).toFloat()
                child.y =
                    (centerY - childRadius) + (orbitRadius - outerOrbitPadding) * cos(
                        Math.toRadians(
                            angle + outerOrbitCurrentAngle
                        )
                    ).toFloat()
            } else if (i < 7) {
                val angle = when (i) {
                    4 -> 20.0
                    5 -> 140.0
                    6 -> 260.0
                    else -> 0.0
                }

                child.x =
                    (centerX - childRadius) - (innerRadius - innerOrbitPaintStrokeWidth / 2) * sin(
                        Math.toRadians(
                            angle + innerOrbitCurrentAngle
                        )
                    ).toFloat()
                child.y =
                    (centerY - childRadius) + (innerRadius - innerOrbitPaintStrokeWidth / 2) * cos(
                        Math.toRadians(
                            angle + innerOrbitCurrentAngle
                        )
                    ).toFloat()
            } else {
                child.x = centerX - childRadius
                child.y = centerY - childRadius
            }
        }
    }

    private fun startAnimation() {
        startOuterOrbitAnimation()
        startInnerOrbitAnimation()
    }

    private fun startOuterOrbitAnimation() {
        val outerOrbitAnimator = createAngleValueAnimator(outerOrbitAnimationDuration)
        outerOrbitAnimator.let {
            it.addUpdateListener { valueAnimator ->
                outerOrbitCurrentAngle = valueAnimator.animatedValue as Float
                invalidate()
            }
        }
        outerOrbitAnimator.start()
    }

    private fun startInnerOrbitAnimation() {
        val innerOrbitAnimator = createAngleValueAnimator(innerOrbitAnimationDuration)
        innerOrbitAnimator.let {
            it.addUpdateListener { valueAnimator ->
                innerOrbitCurrentAngle = valueAnimator.animatedValue as Float
                invalidate()
            }
        }
        innerOrbitAnimator.start()
    }

    private fun createAngleValueAnimator(animationDuration: Int): ValueAnimator {
        // To get more smooth animation on longer duration I switched from Int to Float.
        return ValueAnimator.ofFloat(0f, 360f).apply {
            duration = animationDuration.toLong()
            interpolator = LinearInterpolator()
            repeatCount = ValueAnimator.INFINITE
        }
    }
}