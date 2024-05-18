package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val myView = findViewById<MyView>(R.id.view)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadio = findViewById<RadioButton>(checkedId)
            when (checkedRadio.id) {
                R.id.radioRect -> myView.setShapeType(ShapeType.Rect)
                R.id.radioCircle -> myView.setShapeType(ShapeType.Circle)
            }
        }

    }
}
