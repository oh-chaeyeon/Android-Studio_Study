package com.example.myapplication11

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter (private val viewModel: MyViewModel): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_layout, parent, false)
        val viewHolder = ViewHolder(view)

        view.setOnClickListener {
            viewModel.clickItem.value = viewHolder.adapterPosition
        }

        return viewHolder
    }


    override fun getItemCount(): Int {
        return viewModel.items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val view = holder.itemView
        val text1 = view.findViewById<TextView>(R.id.textView1)
        val text2 = view.findViewById<TextView>(R.id.textView2)

        text1.text= viewModel.items[position].firstname
        text2.text =viewModel.items[position].lastname
    }
}