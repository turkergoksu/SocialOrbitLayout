package me.turkergoksu.lib.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import me.turkergoksu.lib.BitmapUtil
import me.turkergoksu.lib.toDp
import me.turkergoksu.lib.toPx
import me.turkergoksu.socialorbitlayout.*
import me.turkergoksu.lib.model.Orbit
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * Created by turkergoksu on 26-Jan-21.
 */
class SocialOrbitLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    // Member Variables
    private var orbit: Orbit? = null
    private var typedArray: TypedArray? = null

    // Drawing
    private var outerOrbitPaint = Paint()
    private var innerOrbitPaint = Paint()

    // Animation
    private var outerOrbitCurrentAngle = 0f
    private var innerOrbitCurrentAngle = 0f

    init {
        typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SocialOrbitLayout,
            0,
            0
        )
    }

    private fun init() {
        // TODO: 26-Jan-21  https://stackoverflow.com/a/13056400/6771753
        setWillNotDraw(false)

        initAttributes()

        setOrbitPaint()

        addChildFloatingObjectViews()

        startAnimation()
    }

    private fun initAttributes() {
        typedArray?.apply {
            // Outer orbit color
            orbit?.outerOrbitColor =
                getColor(
                    R.styleable.SocialOrbitLayout_outerOrbitColor,
                    Color.LTGRAY
                )

            // FIXME: 29-Jan-21 Alttaki toPx, toDp gibi fonksiyonlar gereksiz islem yapıyor.
            /**
             * Fakat gelistiriciden Orbit nesnesini olustururken px degerleri girmesini istemek de
             * cok sacma olur. Daha temiz bir cözüm bulabilirsen onu implemente et. Bulamazsan
             * bu sekilde kalsın.
              */
            // Outer orbit width
            orbit?.outerOrbitWidth = getDimension(
                R.styleable.SocialOrbitLayout_outerOrbitWidth,
                2f.toPx(resources.displayMetrics)
            ).toDp(resources.displayMetrics)

            // Outer orbit animation duration
            orbit?.outerOrbitAnimationDuration =
                abs(getInt(R.styleable.SocialOrbitLayout_outerOrbitAnimDuration, 60000))

            // Outer orbit padding
            orbit?.outerOrbitPadding = getDimension(
                R.styleable.SocialOrbitLayout_outerOrbitPadding,
                40f.toPx(resources.displayMetrics)
            ).toDp(resources.displayMetrics)

            // Inner orbit radius
            orbit?.innerRadius = getDimension(
                R.styleable.SocialOrbitLayout_innerOrbitRadius,
                90f.toPx(resources.displayMetrics)
            ).toDp(resources.displayMetrics)

            // Inner orbit color
            orbit?.innerOrbitColor = getColor(
                R.styleable.SocialOrbitLayout_innerOrbitColor,
                Color.parseColor("#f8f4fe")
            )

            // Inner orbit width
            orbit?.innerOrbitWidth = getDimension(
                R.styleable.SocialOrbitLayout_innerOrbitWidth,
                40f.toPx(resources.displayMetrics)
            ).toDp(resources.displayMetrics)

            // Inner orbit animation duration
            orbit?.innerOrbitAnimationDuration =
                abs(getInt(R.styleable.SocialOrbitLayout_innerOrbitAnimDuration, 30000))

            recycle()
        }
    }

    private fun setOrbitPaint() {
        // Outer orbit paint
        outerOrbitPaint.style = Paint.Style.STROKE
        outerOrbitPaint.color = orbit?.outerOrbitColor!!
        outerOrbitPaint.strokeWidth = orbit?.outerOrbitWidth!!.toPx(resources.displayMetrics)

        // Inner orbit paint
        innerOrbitPaint.style = Paint.Style.STROKE
        innerOrbitPaint.color = orbit?.innerOrbitColor!!
        innerOrbitPaint.strokeWidth = orbit?.innerOrbitWidth!!.toPx(resources.displayMetrics)
    }

    private fun addChildFloatingObjectViews() {
        for (floatingObject in orbit?.floatingObjects!!) {
            // FIXME: 28-Jan-21 bu for icini düzenle veya fonksiyonlastir
            val view = FloatingObjectView(context)

            floatingObject.bitmap = BitmapUtil.getCircularCroppedBitmap(
                BitmapUtil.getScaledBitmap(
                    floatingObject.bitmap,
                    floatingObject.size,
                    floatingObject.borderWidth,
                    resources.displayMetrics
                )
            )

            view.setFloatingObject(floatingObject)
            view.layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            view.elevation = floatingObject.elevation
            addView(view)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // If this code block doesn't work try in dispatchDraw
        val centerX = (measuredWidth / 2).toFloat()
        val centerY = (measuredHeight / 2).toFloat()
        val outerOrbitRadius = centerX.coerceAtMost(centerY)

        canvas?.drawCircle(
            centerX,
            centerY,
            outerOrbitRadius - orbit?.outerOrbitPadding!!.toPx(resources.displayMetrics),
            outerOrbitPaint
        )

        val innerRadius = orbit!!.innerRadius.toPx(resources.displayMetrics)
        canvas?.drawCircle(
            centerX,
            centerY,
            innerRadius,
            innerOrbitPaint
        )

        // FIXME: 28-Jan-21 is this variable really needed? Use outerOrbitRadius if possible
        val orbitRadius = centerX.coerceAtMost(centerY)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val childRadius = min(child.width, child.height) / 2f

            if (i == 0) {
                child.x = centerX - childRadius
                child.y = centerY - childRadius
            } else if (i < 5) {
                val angle = when (i) {
                    1 -> 70.0
                    2 -> 160.0
                    3 -> 250.0
                    4 -> 340.0
                    else -> 0.0
                }

                child.x =
                    (centerX - childRadius) - (orbitRadius - orbit?.outerOrbitPadding!!.toPx(
                        resources.displayMetrics
                    )) * sin(
                        Math.toRadians(
                            angle + outerOrbitCurrentAngle
                        )
                    ).toFloat()
                child.y =
                    (centerY - childRadius) + (orbitRadius - orbit?.outerOrbitPadding!!.toPx(
                        resources.displayMetrics
                    )) * cos(
                        Math.toRadians(
                            angle + outerOrbitCurrentAngle
                        )
                    ).toFloat()
            } else {
                val angle = when (i) {
                    5 -> 20.0
                    6 -> 140.0
                    7 -> 260.0
                    else -> 0.0
                }

                child.x =
                    (centerX - childRadius) - (innerRadius - orbit?.innerOrbitWidth!!.toPx(
                        resources.displayMetrics
                    ) / 2) * sin(
                        Math.toRadians(
                            angle + innerOrbitCurrentAngle
                        )
                    ).toFloat()
                child.y =
                    (centerY - childRadius) + (innerRadius - orbit?.innerOrbitWidth!!.toPx(
                        resources.displayMetrics
                    ) / 2) * cos(
                        Math.toRadians(
                            angle + innerOrbitCurrentAngle
                        )
                    ).toFloat()
            }
        }
    }

    private fun startAnimation() {
        startOuterOrbitAnimation()
        startInnerOrbitAnimation()
    }

    private fun startOuterOrbitAnimation() {
        val outerOrbitAnimator = createAngleValueAnimator(orbit?.outerOrbitAnimationDuration!!)
        outerOrbitAnimator.let {
            it.addUpdateListener { valueAnimator ->
                outerOrbitCurrentAngle = valueAnimator.animatedValue as Float
                invalidate()
            }
        }
        outerOrbitAnimator.start()
    }

    private fun startInnerOrbitAnimation() {
        val innerOrbitAnimator = createAngleValueAnimator(orbit?.innerOrbitAnimationDuration!!)
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

    fun setOrbit(orbit: Orbit) {
        this.orbit = orbit
        init()
    }
}