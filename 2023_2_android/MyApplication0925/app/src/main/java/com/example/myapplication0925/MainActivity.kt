package com.example.myapplication0925

import android.os.Build.VERSION_CODES.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Scene
import android.transition.TransitionManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MyViewModel : ViewModel() {
    private val _studentName = MutableLiveData<String>("")
    private val _studentAddress = MutableLiveData<String>("")
    private val _staffName = MutableLiveData<String>("")
    private val _staffAddress = MutableLiveData<String>("")

    val studentName: LiveData<String>
        get() = _studentName

    val studentAddress: LiveData<String>
        get() = _studentAddress

    val staffName: LiveData<String>
        get() = _staffName

    val staffAddress: LiveData<String>
        get() = _staffAddress

    fun setStudentData(name: String, address: String) {
        _studentName.value = name
        _studentAddress.value = address
    }

    fun setStaffData(name: String, address: String) {
        _staffName.value = name
        _staffAddress.value = address
    }
}


class MainActivity : AppCompatActivity() {
    private lateinit var scene1: Scene
    private lateinit var scene2: Scene
    private lateinit var editTextName: EditText
    private lateinit var editTextAddr: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radio = findViewById<RadioGroup>(R.id.radioGroup)
        val sceneRoot = findViewById<FrameLayout>(R.id.scene_root)
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.student_layout, this)
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.staff_layout, this)

        radio.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioStudent -> {
                    myViewModel.setStudentData(
                        editTextName.text.toString(),
                        editTextAddr.text.toString()
                    )

                    // Staff 화면으로 전환할 때 Student 화면의 데이터 저장
                    TransitionManager.go(scene1, ChangeBounds())
                    editTextName.setText(myViewModel.studentName.value)
                    editTextAddr.setText(myViewModel.studentAddress.value)
                }
                R.id.radioStaff -> {
                    // Staff 라디오 버튼 선택
                    myViewModel.setStaffData(
                        editTextName.text.toString(),
                        editTextAddr.text.toString()
                    )

                    // Student 화면으로 전환할 때 Staff 화면의 데이터 저장
                    TransitionManager.go(scene2, ChangeBounds())
                    editTextName.setText(myViewModel.staffName.value)
                    editTextAddr.setText(myViewModel.staffAddress.value)
                }
            }
        }

    }
}