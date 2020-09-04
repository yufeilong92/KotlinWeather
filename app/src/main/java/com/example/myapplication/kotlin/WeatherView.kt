package com.example.myapplication.kotlin

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.example.myapplication.kotlin.BaseType
import com.example.myapplication.kotlin.RainTypeImpl


/**
 * Time:2019/9/25
 * Author:xiongms
 * Description:
 */
class WeatherView @kotlin.jvm.JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var weatherType: BaseType? = null

    val refreshAnimator: ValueAnimator = ValueAnimator.ofInt(0).setDuration(Long.MAX_VALUE)

    init {
        refreshAnimator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animator ->
            postInvalidate()
        })
        refreshAnimator.start()
    }

    /**
     * 设置天气类型
     */
    fun setWeatherEnum(enum: WeatherEnum) {
        weatherType?.onDetachedFromWindow()

        when (enum) {
            WeatherEnum.LIGHT_RAIN -> weatherType = RainTypeImpl(context, true)
            WeatherEnum.MODERATE_RAIN -> weatherType =
                RainTypeImpl(context, hasLight = false, hasRain = true, isBigRain = false)
            WeatherEnum.HEAVY_RAIN -> weatherType =
                RainTypeImpl(context, hasLight = false, hasRain = true, isBigRain = true)
            WeatherEnum.CLOUDY_DAY -> weatherType = RainTypeImpl(context, hasLight = false, hasRain = false)
            WeatherEnum.HIGH_TEMP -> weatherType = SunnyTypeImpl(context)
        }

        weatherType?.onSizeChanged(context, width, height)
        weatherType?.onAttachedToWindow()
    }

    /**
     * view窗口大小改变
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        weatherType?.onSizeChanged(context, w, h)
    }

    /**
     * 依附到窗口
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        weatherType?.onAttachedToWindow()
    }

    /**
     * 脱离窗口
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        weatherType?.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        weatherType?.onDraw(context, canvas!!)
    }
}