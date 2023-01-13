package com.example.shaketoonflashlight

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventCallback
import android.hardware.SensorManager
import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.math.abs

class ShakeDetector(context: Context) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var shakeListener: ((Int) -> Unit)? = null

    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var lastZ: Float = 0f
    private var shakeCount = 0

    private val shakeCallback = @RequiresApi(Build.VERSION_CODES.N)
    object : SensorEventCallback() {
        private var lastUpdate: Long = 0

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val curTime = System.currentTimeMillis()
                if (curTime - lastUpdate > 100) {
                    val diffTime = curTime - lastUpdate

                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    val speed = abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000
                    if (speed > SHAKE_THRESHOLD) {
                        shakeCount++
                        if (shakeCount == 2) {
                            shakeListener?.invoke(shakeCount)
                            shakeCount = 0
                        }
                    }

                    lastX = x
                    lastY = y
                    lastZ = z
                    lastUpdate = curTime
                }
            }
        }
    }

    fun setOnShakeListener(listener: (Int) -> Unit) {
        shakeListener = listener
    }

    fun start() {
        sensorManager.registerListener(shakeCallback, accelerometerSensor,
            SensorManager.SENSOR_DELAY_UI
        )
    }

    fun stop() {
        sensorManager.unregisterListener(shakeCallback)
    }
    fun resetShakeCount() {
        shakeCount = 0
    }
    companion object {
        private const val SHAKE_THRESHOLD = 800
    }
}