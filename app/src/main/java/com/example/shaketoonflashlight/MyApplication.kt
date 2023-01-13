package com.example.shaketoonflashlight

import android.app.Application
import android.content.Intent

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startService(Intent(this, SensorService::class.java))
    }
}