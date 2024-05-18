package com.example.myapplication13

import android.annotation.SuppressLint
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
import android.Manifest
import android.os.Binder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {
    private val binder = LocalBinder()
    inner class LocalBinder : Binder(){
        fun getService() = this@MyService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
    //이렇게 하면 count값은 내부에서만 바꿀 수 있음
    var count : Int = 0
        private set

    override fun onCreate() {
        super.onCreate()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startForeground(notificationID, createNotification())
        //createNotification() 디폴트가 0임.
        count ++ //서비스를 시작한 횟수
        CoroutineScope(Dispatchers.IO).apply{
            launch {
                for(i in 1 .. 10){
                    delay(1000)
                    updateNotification(notificationID,createNotification(i*10))
                }
                stopSelf(startId) //이 서비스를 스탑시킴 이걸 코루틴 밖에서 실행시키면 x, 끝날 때 넣어줘야함
            }
        }



        return super.onStartCommand(intent, flags, startId)
    }
    private val channelID = "default"
    private val notificationID = 1
    @RequiresApi(Build.VERSION_CODES.O)//오레오..?
    private fun createNotificationChannel() {
        val channel = NotificationChannel(channelID, "default channel",
            NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "description text of this channel."
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }
    private fun updateNotification(id: Int, notification: Notification) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(this).notify(id, notification)
        }
    }
    private fun createNotification(progress: Int = 0) = NotificationCompat.Builder(this, channelID)
        .setContentTitle("Downloading")
        .setContentText("Downloading a file from a cloud")
        .setSmallIcon(R.drawable.ic_android_black_24dp)
        .setOnlyAlertOnce(true) // importance 에 따라 알림 소리가 날 때, 처음에만 소리나게 함
        .setProgress(100, progress, false)
        .build()
}