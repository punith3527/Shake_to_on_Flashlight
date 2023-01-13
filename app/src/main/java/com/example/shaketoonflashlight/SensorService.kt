package com.example.shaketoonflashlight

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import java.util.*

class SensorService : Service() {

    private val notificationId = 1
    private val channelId = "shake_detection"
    private lateinit var shakeDetector : ShakeDetector
    private var isShakeDetected = false
    private var shakeTimer = Timer()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        shakeDetector = ShakeDetector(this.applicationContext)

        // Create a notification channel for the service
        val channel =
            NotificationChannel(channelId, "Shake Detection", NotificationManager.IMPORTANCE_HIGH)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        // Create a notification for the service
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Shake Detection")
            .setContentText("Monitoring for shake gestures")
            .setSmallIcon(R.drawable.flash)
            .build()

        // Start the service in the foreground
        startForeground(notificationId, notification)

        // Initialize the shake detector
        shakeDetector.setOnShakeListener {
            if (it == 2 && !isShakeDetected) {
                //Toast.makeText(this, "Double shake detected!", Toast.LENGTH_SHORT).show()
                OnButtonPressed().onButtonClick(this)
                isShakeDetected = true
                shakeTimer.schedule(object : TimerTask() {
                    override fun run() {
                        isShakeDetected = false
                    }
                }, 1000)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        shakeDetector.start()
        shakeDetector.resetShakeCount()
        isShakeDetected = false
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        shakeDetector.stop()
        startForeground(1, Notification())
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}