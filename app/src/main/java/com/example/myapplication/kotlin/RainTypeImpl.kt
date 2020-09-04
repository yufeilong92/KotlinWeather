package com.example.myapplication.kotlin

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator
import com.example.myapplication.R
import java.util.*


/**
 * Time:2019/9/26
 * Author:xiongms
 * Description: 阴雨天气
 */
class RainTypeImpl(
    val context: Context,
    val hasLight: Boolean = true,
    val hasRain: Boolean = true,
    val isBigRain: Boolean = true
) : BaseType {

    private var width: Int = 0
    private var height: Int = 0

    // 雨滴集合
    private var mRains: MutableList<RainHolder>? = null
    // 画笔
    private var mPaint: Paint? = null

    private var mLightPaint: Paint? = null

    private var longCloud: Drawable? = null
    private var longCloud1: Drawable? = null
    private var shortCloud: Drawable? = null
    private var lighting: Drawable? = null

    private var longCloudAnimator1: ValueAnimator? = null
    private var longCloudAnimator2: ValueAnimator? = null
    private var shortCloudAnimator: ValueAnimator? = null

    private var lightingAnimator: ValueAnimator? = null
    private var lightingWhiteAnimator: ValueAnimator? = null

    private var rainCount = if (isBigRain) 500 else 200


    private var lightingWhiteAlpha: Int? = null
    private var lightingAlpha: Int? = null


    init {
        longCloud = context.resources.getDrawable(R.drawable.od_rainday_long_cloud)
        longCloud1 = context.resources.getDrawable(R.drawable.od_rainday_long_cloud)
        shortCloud = context.resources.getDrawable(R.drawable.od_rainday_short_cloud)
        lighting = context.resources.getDrawable(R.drawable.od_rain_day_lighting)

        this.longCloudAnimator1 = ValueAnimator.ofFloat(0.0f, 1.0f)
        this.longCloudAnimator1?.repeatCount = ValueAnimator.INFINITE
        this.longCloudAnimator1?.duration = 40000L
        this.longCloudAnimator1?.interpolator = LinearInterpolator()
        this.longCloudAnimator1?.addUpdateListener { paramAnonymousValueAnimator ->
            val animatedValue = paramAnonymousValueAnimator.animatedValue as Float
            this.longCloud?.setBounds(
                -width + (width * animatedValue).toInt(),
                0,
                width + (width * animatedValue).toInt(),
                longCloud?.intrinsicHeight ?: 0
            )
        }

        this.longCloudAnimator2 = ValueAnimator.ofFloat(0.0f, 1.0f)
        this.longCloudAnimator2?.repeatCount = ValueAnimator.INFINITE
        this.longCloudAnimator2?.duration = 20000L
        this.longCloudAnimator2?.interpolator = LinearInterpolator()
        this.longCloudAnimator2?.addUpdateListener(ValueAnimator.AnimatorUpdateListener { paramAnonymousValueAnimator ->
            val animatedValue = paramAnonymousValueAnimator.animatedValue as Float
            this.longCloud1?.setBounds(
                -width + (width * animatedValue).toInt(),
                0,
                width + (width * animatedValue).toInt(),
                longCloud1?.intrinsicHeight ?: 0
            )
        })
        this.shortCloudAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
        this.shortCloudAnimator?.repeatCount = ValueAnimator.INFINITE
        this.shortCloudAnimator?.duration = 25000L
        this.shortCloudAnimator?.interpolator = LinearInterpolator()
        this.shortCloudAnimator?.addUpdateListener { paramAnonymousValueAnimator ->
            val animatedValue = paramAnonymousValueAnimator.animatedValue as Float
            val dheight = shortCloud?.intrinsicHeight ?: 0
            val dwidth = shortCloud?.intrinsicWidth ?: 0
            this.shortCloud?.setBounds(
                (-dwidth + (width + dwidth) * animatedValue).toInt(),
                0,
                (0 + (width + dwidth) * animatedValue).toInt(),
                dheight
            )

            if (animatedValue >= 0.1f && animatedValue < 0.11f) {
                lightingWhiteAnimator?.start()
                lightingAnimator?.start()
            }
        }

        lightingWhiteAnimator = ValueAnimator.ofInt(*intArrayOf(0, 0, 153, 0, 0, 77, 0, 0, 0)).setDuration(1000L)
        lightingWhiteAnimator?.addUpdateListener {
            lightingWhiteAlpha = it.animatedValue as Int?
        }
        lightingAnimator = ValueAnimator.ofInt(*intArrayOf(0, 255, 0, 0, 255, 0, 0, 0, 0)).setDuration(1000L)
        lightingAnimator?.addUpdateListener {
            lightingAlpha = it.animatedValue as Int?
        }

        mLightPaint = Paint()
        mLightPaint?.isAntiAlias = true
        mLightPaint?.color = Color.WHITE
        mLightPaint?.alpha = 90

        mPaint = Paint()
        mPaint?.isAntiAlias = true
        mPaint?.color = context.resources.getColor(R.color.rain_point)
        mPaint?.strokeWidth = 3f
        mRains = mutableListOf()

        lighting?.setBounds(
            width / 2,
            50,
            width / 2 + (lighting?.intrinsicWidth ?: 0),
            50 + (lighting?.intrinsicHeight ?: 0)
        )
    }

    override fun onSizeChanged(context: Context, w: Int, h: Int) {
        width = w
        height = h

        mRains?.clear()
        for (i in 0..rainCount) {
            val rain = RainHolder(
                getRandom(1, width),
                getRandom(1, height),
                getRandom(dp2px(9f), dp2px(15f)),
                getRandom(dp2px(5f), dp2px(9f)),
                getRandom(60, 100)
            )
            mRains?.add(rain)
        }

        lighting?.setBounds(
            0,
            50,
            0 + (lighting?.intrinsicWidth ?: 0),
            50 + (lighting?.intrinsicHeight ?: 0)
        )
    }

    override fun onDraw(context: Context, canvas: Canvas) {
        canvas?.let { canvas ->
            canvas.save()

            if (hasRain) {
                drawRainPoint(canvas)
            }

            this.longCloud?.draw(canvas)
            this.longCloud1?.draw(canvas)
            this.shortCloud?.draw(canvas)

            if (hasLight) {
                drawLighting(canvas)
            }

            canvas.restore()
        }
    }

    override fun onAttachedToWindow() {
        longCloudAnimator1?.start()
        longCloudAnimator2?.start()
        shortCloudAnimator?.start()
    }

    override fun onDetachedFromWindow() {
        longCloudAnimator1?.cancel()
        longCloudAnimator2?.cancel()
        shortCloudAnimator?.cancel()
    }

    /**
     * 绘制闪电
     */
    private fun drawLighting(canvas: Canvas) {

        if (lightingWhiteAlpha != null && lightingWhiteAlpha!! > 0) {
            mLightPaint?.alpha = lightingWhiteAlpha!!
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mLightPaint!!)
        }

        if (lightingAlpha != null && lightingAlpha!! > 0) {
            lighting?.alpha = lightingAlpha!!
            lighting?.draw(canvas)
        }
    }

    /**
     * 绘制雨滴
     */
    private fun drawRainPoint(canvas: Canvas) {
        mRains?.let { mRains ->
            // 画出集合中的雨点
            for (r in mRains) {
                mPaint?.alpha = r.a
                canvas.drawLine(r.x.toFloat(), r.y.toFloat(), r.x.toFloat(), r.y + r.l.toFloat(), mPaint!!)
            }
            // 将集合中的点按自己的速度偏移
            for (r in mRains) {
                r.y += r.s
                if (r.y > height) {
                    r.y = -r.l
                }
            }
        }
    }

    private fun dp2px(dpValue: Float): Int {
        context?.let {
            val scale = it.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }
        return 0
    }

    /**
     * 获取给定两数之间的一个随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 介于最大值和最小值之间的一个随机数
     */
    private fun getRandom(min: Int, max: Int): Int {
        return if (max < min) {
            1
        } else min + Random().nextInt(max - min)
    }

    private inner class RainHolder(
        /**
         * 雨点 x 轴坐标
         */
        internal var x: Int,
        /**
         * 雨点 y 轴坐标
         */
        internal var y: Int,
        /**
         * 雨点长度
         */
        internal var l: Int,
        /**
         * 雨点移动速度
         */
        internal var s: Int,
        /**
         * 雨点透明度
         */
        internal var a: Int
    )
}