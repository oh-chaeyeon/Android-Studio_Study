package com.example.myapplication11

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Item(val firstname: String, val lastname: String)

class MyViewModel : ViewModel() {

    val clickItem = MutableLiveData<Int>()

    val items = ArrayList<Item>()

    fun addItem(item: Item){
        items.add(item)
    }

    fun deleteItem(pos: Int){
        items.removeAt(pos)
    }

}