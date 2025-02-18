package com.example.listitemslab

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class Item(val name: String, val address :String)

class MyViewModel : ViewModel() {
    val itemsListData = MutableLiveData<ArrayList<Item>>()
    val items = ArrayList<Item>()

    val itemClickEvent = MutableLiveData<Int>()
    var itemLongClick = -1

    fun addItem(item: Item) {
        items.add(item)
        itemsListData.value = items // let the observer know the livedata changed
    }
    fun updateItem(pos: Int, item: Item) {
        items[pos] = item
        itemsListData.value = items // 옵저버에게 라이브데이터가 변경된 것을 알리기 위해
    }
    fun deleteItem(pos: Int) {
        items.removeAt(pos)
        itemsListData.value = items
    }

}