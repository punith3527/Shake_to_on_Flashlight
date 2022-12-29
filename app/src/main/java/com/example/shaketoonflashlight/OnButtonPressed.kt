package com.example.shaketoonflashlight

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService


class OnButtonPressed {

    fun onButtonClick(context: Context) {
        val isFlashAvailable = context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        val cameraManager = getSystemService(context, CameraManager::class.java) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]

        if (isFlashAvailable) {
            if (FlashStatus.isFlashOn){
                cameraManager.setTorchMode(cameraId, false)
                FlashStatus.registerFlashlightState(context)
            } else {
                cameraManager.setTorchMode(cameraId, true)
                FlashStatus.registerFlashlightState(context)
            }
        } else {
            Toast.makeText(context, "Flash Not Available", Toast.LENGTH_SHORT).show()
        }
    }
}