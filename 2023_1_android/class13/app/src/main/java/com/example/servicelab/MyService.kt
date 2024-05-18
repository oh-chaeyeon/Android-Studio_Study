package com.example.servicelab

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.Manifest
import android.os.Binder

class MyService : Service() {
    private  val binder = LocalBinder()

    inner class LocalBinder : Binder(){
        fun getService() = this@MyService
    }
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    var tickCount : Int = 0
        private set

    //채널을 만들기 적합한 위치는 oncreate 이다.
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel()
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var n = intent?.getIntExtra("init", 0).toString()
        startForeground(notificationID, createNotification())
        tickCount = n.toInt()

        CoroutineScope(Dispatchers.IO).apply {
            launch {
                for(i in 1..10){
                    delay(1000)
                    tickCount++
                    updateNotification(notificationID,createNotification(i*10))
                }
                //코루틴이 끝날때 해줘야한다.
                stopSelf(startId)
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private val channelID = "default"
    private val notificationID = 1

    @RequiresApi(Build.VERSION_CODES.O)
    //채널만들기
    private fun createNotificationChannel() {
        val channel = NotificationChannel(channelID, "default channel",
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "description text of this channel."
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }

    //실제로 노티파이 알림을 보내는 메소드
    private fun updateNotification(id: Int, notification: Notification) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(this).notify(id, notification)
        }
    }

    //노티피케이션이 실제 알림을 만드는 것
    private fun createNotification(progress: Int = 0) = NotificationCompat.Builder(this, channelID)
        .setContentTitle("Downloading")
        .setContentText("Downloading a file from a cloud")
        .setSmallIcon(R.drawable.ic_android_black_24dp)
        .setOnlyAlertOnce(true) // importance 에 따라 알림 소리가 날 때, 처음에만 소리나게 함
        .setProgress(100, progress, false)
        .build()

}