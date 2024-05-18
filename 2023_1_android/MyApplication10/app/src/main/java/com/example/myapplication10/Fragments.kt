package com.example.myapplication10

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class MyDialogFragments : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //return super.onCreateDialog(savedInstanceState)
        isCancelable = false // OK만 눌렀을 때 없어지도록.
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("Dialog Message")
        builder.setPositiveButton("OK") { dialog, id -> println("OK")}
        return builder.create()

        /*return AlertDialog.Builder(requireActivity()).apply {
            setMessage("Dialog Message")
            setPositiveButton("OK") { dialog, id -> println("OK")}
        }.create() */

    }
}

class Home : Fragment(R.layout.fragment_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = "Home"
    }
}

class Frag1 : Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = "Frag1"
    }
}

class Frag2 : Fragment(R.layout.fragment_layout){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = "Frag2"
    }
}







