package com.example.shaketoonflashlight

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import kotlin.math.abs


class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lastTime: Long = 0
    private var shakeCount = 0
    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var lastZ: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val curTime = System.currentTimeMillis()
            if (curTime - lastTime > 100) {
                val diffTime = curTime - lastTime

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val speed = abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000

                if (speed > SHAKE_THRESHOLD) {
                    shakeCount++
                    if (shakeCount == 3) {
                        val prefs =
                            applicationContext.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                        if (prefs.getBoolean("switch_state", false)) {
                            OnButtonPressed().onButtonClick(context = applicationContext)
                        }
                        shakeCount = 0
                    }
                }
                lastX = x
                lastY = y
                lastZ = z
                lastTime = curTime
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    override fun onResume() {
        sensorManager.registerListener(
            this, sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER
            ), SensorManager.SENSOR_DELAY_NORMAL
        )
        super.onResume()
    }


    override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }

    companion object {
        private const val SHAKE_THRESHOLD = 800
    }
}