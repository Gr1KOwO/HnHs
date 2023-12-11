package com.example.lessnon3_igor.presentation.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BoundService: Service() {

    private val binder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
        Log.i("BoundService", "BoundService onCreate")
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i("BoundService", "BoundService onBind")
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): BoundService = this@BoundService
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("BoundService", "BoundService onDestroy")
    }
}