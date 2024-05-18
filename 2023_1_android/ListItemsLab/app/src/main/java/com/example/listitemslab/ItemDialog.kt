package com.example.listitemslab

import android.graphics.drawable.Icon
import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class ItemDialog(private val itemPos: Int = -1) : BottomSheetDialogFragment() {
    private val viewModel by activityViewModels<MyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ):
            View = inflater.inflate(R.layout.item_dialog_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val editTextName = view.findViewById<EditText>(R.id.editTextName)
        val editTextAddress = view.findViewById<EditText>(R.id.editTextAddress)
        val buttonOk = view.findViewById<Button>(R.id.buttonOK)
        if (itemPos >= 0) { // 생성자 인자로 받은 itemPos가 0보다 크면, 해당 데이터를 찾아서 위젯 내용 초기화
            with(viewModel.items[itemPos]) {
                editTextName.setText(name)
                editTextAddress.setText(address)
            }
        }
        buttonOk.setOnClickListener {
            val item = Item(editTextName.text.toString(), editTextAddress.text.toString())
            if (itemPos < 0) // 뷰모델에 데이터 추가
                viewModel.addItem(item)
            else // 뷰모델 데이터 변경
                viewModel.updateItem(itemPos, item)
            dismiss()
        }
    }
}

