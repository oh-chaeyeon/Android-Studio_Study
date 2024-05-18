package com.example.lab4application

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val buttonInc = findViewById<Button>(R.id.buttonInc)
        val buttonDec = findViewById<Button>(R.id.buttonDec)
        val textView = findViewById<TextView>(R.id.textView)

        var num = intent?.getStringExtra("userdata")?:"0".toInt()
        textView?.setText("${num}")

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val resultIntent = Intent()
                resultIntent.putExtra("resultdata",textView.text )
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        })
        val viewModel = ViewModelProvider(this, MyViewModel.MyViewFactory(num.toString().toInt()))[MyViewModel::class.java]
        viewModel.countLiveData.observe(this){
            findViewById<TextView>(R.id.textView).setText("${it}")
        }

        findViewById<Button>(R.id.buttonInc)?.setOnClickListener{
            viewModel.increaseCount()
        }

        findViewById<Button>(R.id.buttonDec)?.setOnClickListener{
            viewModel.decreaseCount()
        }


    }

    override fun onStart() {
        super.onStart()
        println("##################### SecondActivity - onStart #######################")
    }

    override fun onResume() {
        super.onResume()
        println("##################### SecondActivity - onResume #######################")
    }

    override fun onPause() {
        super.onPause()
        println("##################### SecondActivity - onPause #######################")
    }

    override fun onStop() {
        super.onStop()
        println("##################### SecondActivity - onStop #######################")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("##################### SecondActivity - onDestroy #######################")
    }
}