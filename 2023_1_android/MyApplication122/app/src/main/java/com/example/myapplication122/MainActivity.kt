package com.example.myapplication122

import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.TextView
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bt = findViewById<Button>(R.id.button)
        bt.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
    override fun onStart() {

        super.onStart()
        val tv=findViewById<TextView>(R.id.textView)
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val name = pref.getString("name", "Hello World!")
        val size = pref.getString("reply", "small")
        val isItalic = pref.getBoolean("italic", false)

        tv.text=name
        if (size.equals("10sp")) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10F);
        } else if (size.equals("14sp")) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F);
        } else if (size.equals("20sp")) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F);
        }
        if (isItalic) {
            tv.setTypeface(null, Typeface.ITALIC);
        } else {
            tv.setTypeface(null, Typeface.NORMAL);
        }

        //Snackbar.make(this,tv,"signature ${name}",Snackbar.LENGTH_SHORT).show()


    }
}