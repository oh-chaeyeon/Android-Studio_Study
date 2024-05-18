package com.example.myapplicationbin

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.preference.PreferenceManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()

        val tv = findViewById<TextView>(R.id.textView)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val name = pref.getString("name", "Hello World!")
        val size = pref.getString("size","small")
        val italic = pref.getBoolean("italic", false)

        tv.text = name
        if(size.equals("small")){
            tv.setTextSize(Dimension.SP,10f)
        }
        if(size.equals("medium")){
            tv.setTextSize(Dimension.SP,14f)
        }
        if(size.equals("big")){
            tv.setTextSize(Dimension.SP,20f)
        }

        if(italic) {
            tv.setTypeface(null, Typeface.ITALIC);
        }
        else {
            tv.setTypeface(null, Typeface.NORMAL);
        }



    }
}