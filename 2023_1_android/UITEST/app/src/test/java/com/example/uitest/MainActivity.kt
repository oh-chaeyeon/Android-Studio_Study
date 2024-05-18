package com.example.uitest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<TextView>(R.id.button)
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v:View?){
                println("Like clicked!")
            }
        })
    }
}
