package com.example.shaketoonflashlight

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            context?.startService(Intent(context, SensorService::class.java))
        }
    }
}