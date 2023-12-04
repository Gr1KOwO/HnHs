package com.example.lessnon3_igor.presentation

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.lessnon3_igor.databinding.ActivityMainBinding
import com.example.lessnon3_igor.presentation.services.BoundService
import com.example.lessnon3_igor.presentation.services.StandardService
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {
    private var boundService: BoundService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as BoundService.LocalBinder
            boundService = binder.getService()
            isBound = true
            Log.i("BoundService", "Service connected")
        }
        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
            Log.i("BoundService", "Service disconnected")
        }
    }

    private lateinit var binding:ActivityMainBinding
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }
    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fitContentView()
        askNotificationPermission()

        // Запуск StandartService
        startService(Intent(this, StandardService::class.java))

        // Привязка к BoundService
        val boundIntent = Intent(this, BoundService::class.java)
        bindService(boundIntent, connection, Context.BIND_AUTO_CREATE)

    }

    private fun fitContentView()
    {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
                    alertDialogBuilder
                        .setTitle("Пу пу пу")
                        .setMessage("Нам честно нужно это разрешение.")
                        .setNegativeButton("Не, не надо") { _, _ -> }
                        .setPositiveButton("Ну ладно, давай") { _, _ ->
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    val dialog: AlertDialog = alertDialogBuilder.create()
                    dialog.show()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Отвязываемся от BoundService
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
        //Остановка сервиса
        stopService(Intent(this, StandardService::class.java))
    }
}