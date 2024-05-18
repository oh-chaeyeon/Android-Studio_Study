package com.example.myapplication0921

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication0921.databinding.StudentLayoutBinding

class StudentFragment : Fragment() {
    private lateinit var viewModel: MyViewModel
    private lateinit var binding: StudentLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StudentLayoutBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        // 이전에 입력한 학생 데이터를 가져와 설정
        val studentData = viewModel.studentData.value
        binding.editTextName.setText(studentData?.name)
        binding.editTextAddr.setText(studentData?.addr)

        // TODO: 라디오 버튼 및 EditText 이벤트 처리 추가

        return binding.root
    }

    // TODO: 라디오 버튼 및 EditText 이벤트 처리 메서드 추가
}
