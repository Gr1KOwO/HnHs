package com.example.lessnon3_igor.presentation.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class StandardService:Service() {
    override fun onCreate() {
        super.onCreate()
        Log.i("StandardService", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("StandardService", "onStartCommand")
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("StandardService", "Service destroyed")
    }
}