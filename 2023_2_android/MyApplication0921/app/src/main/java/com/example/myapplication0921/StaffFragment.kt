package com.example.myapplication0921
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication0921.databinding.StaffLayoutBinding

class StaffFragment : Fragment() {
    private lateinit var viewModel: MyViewModel
    private lateinit var binding: StaffLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = StaffLayoutBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)

        // 이전에 입력한 스태프 데이터를 가져와 설정
        val staffData = viewModel.staffData.value
        binding.editTextName.setText(staffData?.name)
        binding.editTextAddr.setText(staffData?.addr)

        // TODO: EditText 이벤트 처리 추가

        return binding.root
    }

    // TODO: EditText 이벤트 처리 메서드 추가
}
