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
import me.turkergoksu.lib.model.FloatingObject
import me.turkergoksu.lib.model.FloatingObjectLocation
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

        initOrbitPaintAttributes()

        addChildFloatingObjectViews()

        startAnimation()
    }

    private fun initAttributes() {
        typedArray?.apply {
            // Outer orbit color
            val outerOrbitColor =
                    getColor(
                            R.styleable.SocialOrbitLayout_outerOrbitColor,
                            orbit?.outerOrbitColor!!
                    )

            // Outer orbit width
            val outerOrbitWidth = getDimension(
                    R.styleable.SocialOrbitLayout_outerOrbitWidth,
                    orbit?.outerOrbitWidth!!.toPx(resources.displayMetrics)
            ).toDp(resources.displayMetrics)

            // Outer orbit animation duration
            val outerOrbitAnimationDuration =
                    abs(getInt(R.styleable.SocialOrbitLayout_outerOrbitAnimDuration, orbit?.outerOrbitAnimationDuration!!))

            // Outer orbit padding
            val outerOrbitPadding = getDimension(
                    R.styleable.SocialOrbitLayout_outerOrbitPadding,
                    orbit?.outerOrbitPadding!!.toPx(resources.displayMetrics)
            ).toDp(resources.displayMetrics)

            // Outer orbit start angle
            val outerOrbitStartAngle = getInt(
                    R.styleable.SocialOrbitLayout_outerOrbitStartAngle,
                    orbit?.outerOrbitStartAngle!!.toInt()
            ).toDouble()

            // Outer orbit angle distance
            val outerOrbitAngleDistance = abs(getInt(
                    R.styleable.SocialOrbitLayout_outerOrbitAngleDistance,
                    orbit?.outerOrbitAngleDistance!!.toInt()
            )).toDouble()

            // Inner orbit radius
            val innerOrbitRadius = getDimension(
                    R.styleable.SocialOrbitLayout_innerOrbitRadius,
                    orbit?.innerOrbitRadius!!.toPx(resources.displayMetrics)
            ).toDp(resources.displayMetrics)

            // Inner orbit color
            val innerOrbitColor = getColor(
                    R.styleable.SocialOrbitLayout_innerOrbitColor,
                    orbit?.innerOrbitColor!!
            )

            // Inner orbit width
            val innerOrbitWidth = getDimension(
                    R.styleable.SocialOrbitLayout_innerOrbitWidth,
                    orbit?.innerOrbitWidth!!.toPx(resources.displayMetrics)
            ).toDp(resources.displayMetrics)

            // Inner orbit animation duration
            val innerOrbitAnimationDuration =
                    abs(getInt(R.styleable.SocialOrbitLayout_innerOrbitAnimDuration, orbit?.innerOrbitAnimationDuration!!))

            // Inner orbit start angle
            val innerOrbitStartAngle = getInt(
                    R.styleable.SocialOrbitLayout_innerOrbitStartAngle,
                    orbit?.innerOrbitStartAngle!!.toInt()
            ).toDouble()

            // Inner orbit angle distance
            val innerOrbitAngleDistance = abs(getInteger(
                    R.styleable.SocialOrbitLayout_innerOrbitAngleDistance,
                    orbit?.innerOrbitAngleDistance!!.toInt()
            )).toDouble()

            orbit = Orbit.Builder()
                    .setFloatingObjectList(orbit?.floatingObjectList ?: mutableListOf())
                    .setOuterOrbitColor(outerOrbitColor)
                    .setOuterOrbitWidth(outerOrbitWidth)
                    .setOuterOrbitAnimDuration(outerOrbitAnimationDuration)
                    .setOuterOrbitPadding(outerOrbitPadding)
                    .setOuterOrbitStartAngle(outerOrbitStartAngle)
                    .setOuterOrbitAngleDistance(outerOrbitAngleDistance)
                    .setInnerOrbitRadius(innerOrbitRadius)
                    .setInnerOrbitColor(innerOrbitColor)
                    .setInnerOrbitWidth(innerOrbitWidth)
                    .setInnerOrbitAnimDuration(innerOrbitAnimationDuration)
                    .setInnerOrbitStartAngle(innerOrbitStartAngle)
                    .setInnerOrbitAngleDistance(innerOrbitAngleDistance)
                    .build()

            recycle()
        }
    }

    private fun initOrbitPaintAttributes() {
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
        for (floatingObject in orbit?.floatingObjectList!!) {
            val childView = createFloatingObjectView(floatingObject)
            addView(childView)
        }
    }

    private fun createFloatingObjectView(floatingObject: FloatingObject): FloatingObjectView {
        val view = FloatingObjectView(context)

        floatingObject.bitmap = BitmapUtil.getCircularCroppedBitmap(
                BitmapUtil.getScaledBitmap(
                        floatingObject.bitmap,
                        floatingObject.size,
                        floatingObject.borderWidth,
                        resources.displayMetrics
                )
        )
        view.layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.elevation = floatingObject.elevation
        view.setFloatingObject(floatingObject)

        return view
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // If this code block doesn't work try in dispatchDraw
        val centerX = (measuredWidth / 2).toFloat()
        val centerY = (measuredHeight / 2).toFloat()
        val halfOfWholeView = centerX.coerceAtMost(centerY)

        val outerOrbitRadius = halfOfWholeView - orbit?.outerOrbitPadding!!.toPx(resources.displayMetrics)
        canvas?.drawCircle(
                centerX,
                centerY,
                outerOrbitRadius,
                outerOrbitPaint
        )

        val innerOrbitRadius = orbit!!.innerOrbitRadius!!.toPx(resources.displayMetrics)
        canvas?.drawCircle(
                centerX,
                centerY,
                innerOrbitRadius,
                innerOrbitPaint
        )

        var outerAngle = orbit?.outerOrbitStartAngle!!
        var innerAngle = orbit?.innerOrbitStartAngle!!
        for (i in 0 until childCount) {
            val child = getChildAt(i) as FloatingObjectView
            val childRadius = min(child.width, child.height) / 2f

            when (child.getFloatingObjectLocation()) {
                FloatingObjectLocation.OUTER -> {
                    outerAngle += orbit?.outerOrbitAngleDistance!!

                    child.x =
                            (centerX - childRadius) - (halfOfWholeView - orbit?.outerOrbitPadding!!.toPx(
                                    resources.displayMetrics
                            )) * sin(
                                    Math.toRadians(
                                            outerAngle + outerOrbitCurrentAngle
                                    )
                            ).toFloat()
                    child.y =
                            (centerY - childRadius) + (halfOfWholeView - orbit?.outerOrbitPadding!!.toPx(
                                    resources.displayMetrics
                            )) * cos(
                                    Math.toRadians(
                                            outerAngle + outerOrbitCurrentAngle
                                    )
                            ).toFloat()
                }
                FloatingObjectLocation.INNER -> {
                    innerAngle += orbit?.innerOrbitAngleDistance!!

                    child.x =
                            (centerX - childRadius) - (innerOrbitRadius - orbit?.innerOrbitWidth!!.toPx(
                                    resources.displayMetrics
                            ) / 2) * sin(
                                    Math.toRadians(
                                            innerAngle + innerOrbitCurrentAngle
                                    )
                            ).toFloat()
                    child.y =
                            (centerY - childRadius) + (innerOrbitRadius - orbit?.innerOrbitWidth!!.toPx(
                                    resources.displayMetrics
                            ) / 2) * cos(
                                    Math.toRadians(
                                            innerAngle + innerOrbitCurrentAngle
                                    )
                            ).toFloat()
                }
                FloatingObjectLocation.CENTER -> {
                    child.x = centerX - childRadius
                    child.y = centerY - childRadius
                }
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