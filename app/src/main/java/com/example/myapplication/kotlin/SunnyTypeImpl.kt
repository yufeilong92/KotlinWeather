package com.example.myapplication.kotlin

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.view.animation.LinearInterpolator
import com.example.myapplication.R

/**
 * Time:2019/9/27
 * Author:xiongms
 * Description:
 */
class SunnyTypeImpl(val context: Context) : BaseType {


    val sunWidth = dp2px(128.0f)

    // 太阳
    private var sunDrawable = context.resources.getDrawable(R.drawable.od_detail_weather_high_temp_sun)
    // 光晕
    private var haloDrawable = context.resources.getDrawable(R.drawable.od_weather_clear_day_halo)
    // 小光辉
    private var smallRadiance = context.resources.getDrawable(R.drawable.od_detail_weather_high_temp_radiance_small)
    // 大光辉
    private var bigRadiance = context.resources.getDrawable(R.drawable.od_detail_weather_high_temp_radiance_big)


    private var haloRadianceAnimator: ValueAnimator
    private var smallRadianceAnimator: ValueAnimator
    private var bigRadianceAnimator: ValueAnimator
    private var haloRatate: Float = 0f
    private var smallRadianceRatate: Float = 0f
    private var bigRadianceRatate: Float = 0f

    init {
        haloRadianceAnimator = ValueAnimator.ofFloat(*floatArrayOf(-45.0f, 130.0f))
        haloRadianceAnimator.repeatCount = ValueAnimator.INFINITE
        haloRadianceAnimator.duration = 30000L
        haloRadianceAnimator.interpolator = LinearInterpolator()
        haloRadianceAnimator.addUpdateListener { animator ->
            haloRatate = (animator.animatedValue as Float).toFloat()
        }

        smallRadianceAnimator = ValueAnimator.ofFloat(*floatArrayOf(0.0f, 360.0f))
        smallRadianceAnimator.repeatCount = ValueAnimator.INFINITE
        smallRadianceAnimator.duration = 30000L
        smallRadianceAnimator.interpolator = LinearInterpolator()
        smallRadianceAnimator.addUpdateListener { animator ->
            smallRadianceRatate = (animator.animatedValue as Float).toFloat()
        }
        bigRadianceAnimator = ValueAnimator.ofFloat(*floatArrayOf(0.0f, 360.0f))
        bigRadianceAnimator.repeatCount = ValueAnimator.INFINITE
        bigRadianceAnimator.duration = 60000L
        bigRadianceAnimator.interpolator = LinearInterpolator()
        bigRadianceAnimator.addUpdateListener { animator ->
            bigRadianceRatate = (animator.animatedValue as Float).toFloat()
        }
        var dHeight = smallRadiance.intrinsicHeight
        var dWidth = smallRadiance.intrinsicWidth
        smallRadiance.setBounds(-dWidth / 2, -dHeight / 2, dWidth / 2, dHeight / 2)

        dHeight = bigRadiance.intrinsicHeight
        dWidth = bigRadiance.intrinsicWidth
        bigRadiance.setBounds(-dWidth / 2, -dHeight / 2, dWidth / 2, dHeight / 2)

        dHeight = haloDrawable.intrinsicHeight
        dWidth = haloDrawable.intrinsicWidth
        haloDrawable.setBounds(0, -(dHeight / 2), dWidth, dHeight / 2)
    }

    override fun onSizeChanged(context: Context, w: Int, h: Int) {
    }

    override fun onDraw(context: Context, canvas: Canvas) {
        // 绘制光晕
        canvas.save()
        canvas.rotate(haloRatate)
        haloDrawable.draw(canvas)
        canvas.restore()

        // 绘制小光晕
        canvas.save()
        canvas.rotate(smallRadianceRatate)
        smallRadiance.draw(canvas)
        canvas.restore()
        // 绘制大光晕
        canvas.save()
        canvas.rotate(bigRadianceRatate)
        bigRadiance.draw(canvas)
        canvas.restore()
        // 绘制太阳
        sunDrawable.setBounds(0, 0, sunWidth, sunWidth)
        sunDrawable.draw(canvas)
    }


    override fun onAttachedToWindow() {
        haloRadianceAnimator.start()
        smallRadianceAnimator.start()
        bigRadianceAnimator.start()
    }

    override fun onDetachedFromWindow() {
        haloRadianceAnimator.cancel()
        smallRadianceAnimator.cancel()
        bigRadianceAnimator.cancel()
    }


    private fun dp2px(dpValue: Float): Int {
        context.let {
            val scale = it.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
        return 0
    }

}