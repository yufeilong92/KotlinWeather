package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val builder = FallObject.Builder(resources.getDrawable(R.drawable.ic_launcher_background))
        val build = builder.setSpeed(10, true)
            .setSize(50, 50, true)
            .setWind(5, true, true)
            .build()
        fallingview.addFallObject(build,20)
    }
//    setSpeed(int speed)	设置物体的初始下落速度
//    setSpeed(int speed,boolean isRandomSpeed)	设置物体的初始下落速度，isRandomSpeed：物体初始下降速度比例是否随机
//    setSize(int w, int h)	设置物体大小
//    setSize(int w, int h, boolean isRandomSize)	设置物体大小，isRandomSize：物体初始大小比例是否随机
//    setWind(int level,boolean isWindRandom,boolean isWindChange)	设置风力等级、方向以及随机因素，level：风力等级，isWindRandom：物体初始风向和风力大小比例是否随机，isWindChange：在物体下落过程中风的风向和风力是否会产生随机变化
}