package com.example.homefragment


import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class MyDialogFragments : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //super.onCreate(savedInstanceState)
        isCancelable = false
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("2171123")
        builder.setMessage("오채연")
        builder.setPositiveButton("OK") { dialog, id -> println("OK")}
        return builder.create()
    }
}

class Home : Fragment(R.layout.fragment_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = "HomeFragment"
    }
}

class Page1 : Fragment(R.layout.fragment_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = "Page1Fragment"
    }
}

class Page2 : Fragment(R.layout.fragment_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = "Page2Fragment"
    }
}

class Dialog : Fragment(R.layout.fragment_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = "MyDialogFragment"
    }
}