package com.example.lab4application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val msg = it.data?.getStringExtra("resultdata")?:""
            findViewById<EditText>(R.id.editText).setText(msg)
        }

        button?.setOnClickListener {
            var num = findViewById<EditText>(R.id.editText).text
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("userdata", "${num}")
            activityResult.launch(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        println("##################### MainActivity - onStart #######################")
    }

    override fun onResume() {
        super.onResume()
        println("##################### MainActivity - onResume #######################")
    }

    override fun onPause() {
        super.onPause()
        println("##################### MainActivity - onPause #######################")
    }

    override fun onStop() {
        super.onStop()
        println("##################### MainActivity - onStop #######################")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("##################### MainActivity - onDestroy #######################")
    }
}