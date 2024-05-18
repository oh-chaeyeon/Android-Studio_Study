package com.example.myapplication0921

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication0921.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel
    private lateinit var binding: ActivityMainBinding // View Binding 객체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        // View Binding을 사용하여 뷰 참조
        binding.radioStudent.setOnClickListener { showStudentLayout() }
        binding.radioStaff.setOnClickListener { showStaffLayout() }

        // 초기 화면 설정
        showInitialLayout()
    }

    private fun showInitialLayout() {
        val selectedId = viewModel.selectedRadioButtonId.value
        if (selectedId == R.id.radioStudent) {
            showStudentLayout()
        } else if (selectedId == R.id.radioStaff) {
            showStaffLayout()
        }
    }

    private fun showStudentLayout() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, StudentFragment())
            .commit()

        // 라디오 버튼 클릭 시 선택된 ID 업데이트
        viewModel.saveSelectedRadioButtonId(R.id.radioStudent)
    }

    private fun showStaffLayout() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, StaffFragment())
            .commit()

        // 라디오 버튼 클릭 시 선택된 ID 업데이트
        viewModel.saveSelectedRadioButtonId(R.id.radioStaff)
    }
}
