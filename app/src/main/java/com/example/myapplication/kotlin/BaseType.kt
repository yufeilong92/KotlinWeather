package com.example.myapplication.kotlin

import android.content.Context
import android.graphics.Canvas

/**
 * Time:2019/9/26
 * Author:xiongms
 * Description:
 */
interface BaseType {

    /**
     * 尺寸改变
     */
    fun onSizeChanged(context: Context, w: Int, h: Int)


    /**
     * 绘制
     */
    fun onDraw(context: Context,canvas: Canvas)

    fun onAttachedToWindow()

    fun onDetachedFromWindow()

}