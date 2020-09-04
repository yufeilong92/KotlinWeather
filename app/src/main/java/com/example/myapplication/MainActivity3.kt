package com.example.myapplication

import android.graphics.PixelFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.kotlin.WeatherEnum
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        // 雷雨天气
        weather_view.setWeatherEnum(WeatherEnum.LIGHT_RAIN)
        // 高温天气
//        weather_view.setWeatherEnum(WeatherEnum.HIGH_TEMP)
        // 阴天天气
//        weather_view.setWeatherEnum(WeatherEnum.CLOUDY_DAY)
        // 阴天小雨天气
//        weather_view.setWeatherEnum(WeatherEnum.MODERATE_RAIN)
        // 雷大雨天气
//        weather_view.setWeatherEnum(WeatherEnum.HEAVY_RAIN)
    }

}