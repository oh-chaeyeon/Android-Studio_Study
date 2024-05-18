package com.example.myapplication7

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

class MyViewModel(context: Context) : ViewModel() {
    private val repository = MyRepository(context) // repository 객체 생성
    val repos = repository.repos // repository가 제공하는 repos를 repos 속성에 그대로 연결

    class Factory(val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel > create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MyViewModel(context) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}

class MyAdapter (val items:List<Repo>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v){
        val tvRepo = v.findViewById<TextView>(R.id.tvRepo)
        val tvOwner = v.findViewById<TextView>(R.id.tvOwner)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_layout, parent, false)
        val viewHolder = MyViewHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvRepo.text = items[position].name
        holder.tvOwner.text = items[position].owner.login
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var myViewModel : MyViewModel
    private lateinit var usernameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val api = retrofit.create(RestApi::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyAdapter(emptyList())

        myViewModel = ViewModelProvider(this, MyViewModel.Factory(this)).get(MyViewModel::class.java)
        myViewModel.repos.observe(this) { reposD ->

            val repos = reposD.map {
                Repo(it.name, Owner(it.owner), "")
            }
            recyclerView.adapter = MyAdapter(repos)

        }

        usernameEditText = findViewById(R.id.editUsername)
        val startWorkerButton = findViewById<Button>(R.id.startWorker)
        startWorkerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            startWorker(username)
        }
    }

    private fun startWorker(username: String) {
        val inputData = workDataOf("username" to username)
        val constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.UNMETERED)
            setRequiresBatteryNotLow(true)
        }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<MyWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            MyWorker.name,
            ExistingPeriodicWorkPolicy.UPDATE,
            repeatingRequest)
    }

    private fun stopWorker() {
        WorkManager.getInstance(this).cancelUniqueWork(MyWorker.name)
    }
}