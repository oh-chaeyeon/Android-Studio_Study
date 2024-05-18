package com.example.myapplication09142

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS
import android.provider.Settings.EXTRA_APP_PACKAGE

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 권한 요청을 위한 함수 호출
        requestSinglePermission(Manifest.permission.POST_NOTIFICATIONS)

        // 두 개의 알림 채널 생성
        createNotificationChannel("channel1", "Channel 1")
        createNotificationChannel("channel2", "Channel 2")

        // 버튼과 EditText 요소를 레이아웃에서 찾음
        val buttonNotify1 = findViewById<Button>(R.id.notify1)
        val buttonNotify2 = findViewById<Button>(R.id.notify2)
        val buttonsettings = findViewById<Button>(R.id.settings)
        val editTextNotification = findViewById<EditText>(R.id.editTextNotification)

        var notificationCounter = 0 // 알림 횟수를 저장할 변수를 추가

        // notify1 버튼 클릭 이벤트 핸들러
        buttonNotify1.setOnClickListener {
            notificationCounter++ // 버튼을 누를 때마다 알림 횟수 증가
            showNotification("channel1", "Notification #$notificationCounter")
        }

        // notify2 버튼 클릭 이벤트 핸들러
        buttonNotify2.setOnClickListener {
            val notificationText = editTextNotification.text.toString()
            showNotification("channel2", notificationText)
        }

        buttonsettings.setOnClickListener {
            val intent = Intent(ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(EXTRA_APP_PACKAGE, packageName);
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelID: String, channelName: String) {
        // 알림 채널 생성
        val channel = NotificationChannel(
            channelID, channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Description for $channelName"
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun showNotification(channelID: String, contentText: String) {
        val builder = NotificationCompat.Builder(this, channelID)
        builder.setSmallIcon(R.mipmap.ic_launcher)

        // 알림 제목을 버튼에 따라 다르게 설정
        val title = if (channelID == "channel1") "Notification Lab" else "Notification Lab2"
        builder.setContentTitle(title)

        builder.setContentText(contentText)
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notification = builder.build()
        if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            // 알림 표시
            NotificationManagerCompat.from(this).notify(channelID.hashCode(), notification)
        }
    }

    private fun requestSinglePermission(permission: String) {
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED)
            return

        // 권한 요청을 처리하는 함수
        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) { // 권한이 거부된 경우 경고 대화 상자 표시
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage("Permission not granted: $permission")
                }.show()
            }
        }

        if (shouldShowRequestPermissionRationale(permission)) {
            // 권한에 대한 이유를 설명하는 대화 상자 표시
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage("Permission required for notifications: $permission")
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
            // 권한 요청을 진행
            requestPermLauncher.launch(permission)
        }
    }
}