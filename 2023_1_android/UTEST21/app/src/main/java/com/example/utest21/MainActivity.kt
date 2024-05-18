package com.example.utest21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val radioDog = findViewById<RadioButton>(R.id.radioDog)
        val radioCat = findViewById<RadioButton>(R.id.radioCat)

        val textView = findViewById<TextView>(R.id.textView)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)


        button.setOnClickListener{
            when(radioGroup.checkedRadioButtonId){
                R.id.radioCat -> textView.text = radioCat.text;
                R.id.radioDog -> textView.text = radioDog.text;
            }
        //    val pet = "Dog:${radioDog.isChecked}, Cat: ${radioCat.isChecked}"
        //   Snackbar.make(it, pet, Snackbar.LENGTH_SHORT).show()
        }
    }
}