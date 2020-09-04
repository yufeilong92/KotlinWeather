package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var imageView: ImageView? = null
    private var mColud: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.rotate)
        imageView = findViewById(R.id.id_img) as ImageView
        mColud = findViewById(R.id.id_img_clod) as ImageView
        imageView?.setAnimation(animation)

        animation.fillAfter = true //保存动画执行后的效果

        init(mColud!!)
        initRain()
    }

    private fun initRain() {
        snow_view.LoadSnowImage()
        // 获取当前屏幕的高和宽
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        snow_view.SetView(dm.heightPixels, dm.widthPixels)
        // 更新当前雨滴
        // 更新当前雨滴
        update()
    }

    /*
     * 负责做界面更新工作 ，实现下雨
     */
    private val mRedrawHandler = RefreshHandler()

    inner class RefreshHandler : Handler() {
        override fun handleMessage(msg: Message) {
            //snow.addRandomSnow();
            snow_view.invalidate()
            sleep(10)
        }

        fun sleep(delayMillis: Long) {
            this.removeMessages(0)
            sendMessageDelayed(obtainMessage(0), delayMillis)
        }
    }

    private fun  update(){
        snow_view.addRandomSnow()
        mRedrawHandler.sleep(600)
    }

    private fun init(view: View) {
        //使用组合动画，先把云朵移除去，然后移动进来
        val animation: Animation = TranslateAnimation(0.0f, -800.0f, 0.0f, 0.0f)
        animation.duration = 100
        val moveout: Animation = TranslateAnimation(-800.0f, 1900.0f, 0.0f, 0.0f)
        val set = AnimationSet(true)
        set.addAnimation(animation)
        set.addAnimation(moveout)
        set.fillAfter = true
        set.duration = 5000
        view.animation = set
        set.startNow()
    }
}