package com.example.myapplication13

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.*
import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder

class MainActivity : AppCompatActivity() {
    val textView by lazy{findViewById<TextView>(R.id.textView)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            requestSinglePermission(Manifest.permission.POST_NOTIFICATIONS)
        }
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            startCoroutine()
        }

        val buttonService = findViewById<Button>(R.id.buttonService)
        buttonService.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                startForegroundService(intent)
            }
        }
        val buttonGetCount = findViewById<Button>(R.id.buttonGetCount)
        buttonGetCount.setOnClickListener {
            textView.text = "count: ${myService?.count}"
        }
    }
    private var myService : MyService?= null
    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            myService = (service as MyService.LocalBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MyService::class.java)
        bindService(intent, serviceConnection, BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }

    fun startCoroutine() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            for (i in 1..10) {
                delay(1000)
                withContext(Dispatchers.Main) {
                    textView.text = "$i"
                }
            }
        }
        scope.launch {
            delay(500)
            for (i in 10..20) {
                delay(1000)
                withContext(Dispatchers.Main) {
                    textView.text = "$i"
                }
            }
        }
    }
    private fun requestSinglePermission(permission: String) { // 한번에 하나의 권한만 요청하는 예제
        if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) // 권한 유무 확인
            return
        val requestPermLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { // 권한 요청 컨트랙트
            if (it == false) { // permission is not granted!
                AlertDialog.Builder(this).apply {
                    setTitle("Warning")
                    setMessage(getString(R.string.no_permission, permission))
                }.show()
            }
        }
        if (shouldShowRequestPermissionRationale(permission)) { // 권한 설명 필수 여부 확인
// you should explain the reason why this app needs the permission.
            AlertDialog.Builder(this).apply {
                setTitle("Reason")
                setMessage(getString(R.string.req_permission_reason, permission))
                setPositiveButton("Allow") { _, _ -> requestPermLauncher.launch(permission) }
                setNegativeButton("Deny") { _, _ -> }
            }.show()
        } else {
// should be called in onCreate()
            requestPermLauncher.launch(permission) // 권한 요청 시작
        }
    }
}