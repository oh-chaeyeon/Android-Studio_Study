package com.example.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Scene
import android.transition.TransitionManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    private val _studentName = MutableLiveData<String>("")
    private val _studentAddress = MutableLiveData<String>("")
    private val _staffName = MutableLiveData<String>("")
    private val _staffAddress = MutableLiveData<String>("")

    private val _selectedRadio = MutableLiveData<Int>(R.id.radioGroup2)
    val selectedRadio: LiveData<Int>
        get() = _selectedRadio

    fun setSelectedRadio(checkedId: Int) {
        _selectedRadio.value = checkedId
    }

    val studentName:LiveData<String>
        get() = _studentName

    val studentAddress: LiveData<String>
        get() = _studentAddress

    val staffName: LiveData<String>
        get() = _staffName

    val staffAddress: LiveData<String>
        get() = _staffAddress

    fun setStudentData(name: String, address: String){
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
    private var editTextName: EditText ?= null
    private var editTextAddr: EditText ?= null
    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val radio = findViewById<RadioGroup>(R.id.radioGroup)
        val sceneRoot = findViewById<FrameLayout>(R.id.scene_root)

        val selectedRadio = myViewModel.selectedRadio
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.student_layout,this)
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.staff_layout, this)

        radio.setOnCheckedChangeListener{_, checkedId ->
            when (checkedId) {
                R.id.radioStudent -> {

                    if(editTextName == null){
                        myViewModel.setStaffData(
                            "",
                            editTextAddr?.text.toString()
                        )
                    } else if(editTextAddr == null){
                        myViewModel.setStaffData(
                            editTextName?.text.toString(),
                            "",
                        )
                    } else if (editTextName == null && editTextAddr == null){
                        myViewModel.setStaffData(
                            "",
                            ""
                        )
                    } else {
                        myViewModel.setStaffData(
                            editTextName?.text.toString(),
                            editTextAddr?.text.toString()
                        )
                    }




                    TransitionManager.go(scene1, ChangeBounds())

                    editTextName = findViewById(R.id.editTextName)
                    editTextAddr = findViewById(R.id.editTextAddr)

                    editTextName?.setText(myViewModel.studentName.value)
                    editTextAddr?.setText(myViewModel.studentAddress.value)

                    val radioStu = scene1.sceneRoot.findViewById<RadioGroup>(R.id.radioGroup2)

                    //staff 라디오버튼을 누를때 student화면에서 누른 radioStu인 라디오 그룹 중에
                    // 선택된 라디오버튼을 myViewModel에 정보를 저장!

                    myViewModel.selectedRadio.observe(this) { selectedId ->
                        radioStu.check(selectedId)
                    }

                    radioStu.setOnCheckedChangeListener { _, checkedId ->
                        myViewModel.setSelectedRadio(checkedId)
                    }
                }

                R.id.radioStaff -> {

                    if(editTextName == null){
                        myViewModel.setStudentData(
                            "",
                            editTextAddr?.text.toString()
                        )
                    } else if(editTextAddr == null){
                        myViewModel.setStudentData(
                            editTextName?.text.toString(),
                            ""
                        )
                    } else if (editTextName == null && editTextAddr ==null){
                        myViewModel.setStudentData(
                            "",
                            ""
                        )
                    } else {
                        myViewModel.setStudentData(
                            editTextName?.text.toString(),
                            editTextAddr?.text.toString()
                        )
                    }




                    TransitionManager.go(scene2, ChangeBounds())


                    editTextName = findViewById(R.id.editTextName)
                    editTextAddr = findViewById(R.id.editTextAddr)
                    editTextName?.setText(myViewModel.staffName.value)
                    editTextAddr?.setText(myViewModel.staffAddress.value)
                }
            }
        }
    }
}
