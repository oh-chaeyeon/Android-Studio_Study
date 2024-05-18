package com.example.fragmentlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}


class HomeFragment : Fragment(R.layout.home_fragment) {
    val viewModel : MyViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = viewModel.countLiveData.value.toString()
        view.findViewById<Button>(R.id.button).setOnClickListener {

            viewModel.increaseCount()
            findNavController().navigate(R.id.action_homeFragment_to_frag1Fragment)
        }

        /*val viewModel : MyViewModel by activityViewModels()
        viewModel.myValue.observe(viewLifecycleOwner) {
            println("Frag1 ################## {$it} ###################")
        }*/


    }


}


class Frag1Fragment : Fragment(R.layout.nav1_fragment){
    val viewModel : MyViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = viewModel.countLiveData.value.toString()
        view.findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.increaseCount()
            findNavController().navigate(R.id.action_frag1Fragment_to_frag2Fragment)
        }

        /*val viewModel : MyViewModel by activityViewModels()
        viewModel.myValue.observe(viewLifecycleOwner) {
            println("Frag1 ################## {$it} ###################")
        }*/


    }
}

class Frag2Fragment : Fragment(R.layout.nav2_fragment){
    val viewModel : MyViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textView).text = viewModel.countLiveData.value.toString()
        view.findViewById<Button>(R.id.button).setOnClickListener {
            viewModel.increaseCount()
            findNavController().navigate(R.id.action_frag2Fragment_to_homeFragment)
        }

        /*val viewModel : MyViewModel by activityViewModels()
        viewModel.myValue.observe(viewLifecycleOwner) {
            println("Frag2 ################## {$it} ###################")
        }*/

    }
}