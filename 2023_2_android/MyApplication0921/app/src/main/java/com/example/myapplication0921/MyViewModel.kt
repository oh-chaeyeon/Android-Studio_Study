package com.example.myapplication0921

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    data class StudentData(val name: String, val addr: String)
    data class StaffData(val name: String, val addr: String)

    val studentData = MutableLiveData<StudentData>()
    val staffData = MutableLiveData<StaffData>()
    val selectedRadioButtonId = MutableLiveData<Int>()

    var previousStudentData: StudentData? = null
    var previousStaffData: StaffData? = null

    init {
        studentData.value = StudentData("", "")
        staffData.value = StaffData("", "")
        selectedRadioButtonId.value = R.id.radioStudent
    }

    fun saveStudentData(name: String, addr: String) {
        val student = StudentData(name, addr)
        studentData.value = student
        previousStudentData = student
    }

    fun saveStaffData(name: String, addr: String) {
        val staff = StaffData(name, addr)
        staffData.value = staff
        previousStaffData = staff
    }

    fun saveSelectedRadioButtonId(id: Int) {
        selectedRadioButtonId.value = id
    }
}
