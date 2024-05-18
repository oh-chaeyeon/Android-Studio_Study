package com.example.myapplication6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import kotlinx.coroutines.*

data class Owner(val login: String)
data class Repo(val name: String, val owner: Owner, val url: String)


interface RestApi {
    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>
}

class MyAdapter(var items:List<Repo>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
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

    override fun getItemCount(): Int { //아이템 갯수
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val repo = items[position]

        // 리포지토리 이름을 tvRepo TextView에 설정합니다.
        holder.tvRepo.text = "${repo.name}"

        // 소유자의 로그인 이름을 tvOwner TextView에 설정합니다.
        holder.tvOwner.text = "${repo.owner.login}"
    }

    // MyAdapter 클래스에 다음 메서드를 추가하여 데이터를 업데이트할 수 있도록 합니다.
    fun updateData(newItems: List<Repo>) {
        items = newItems
        notifyDataSetChanged()
    }

}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")  //baseURL == 깃허브
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val api = retrofit.create(RestApi::class.java) // RestApi 인터페이스는 다음 슬라이드에 설명

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = MyAdapter(emptyList())
        recyclerView.adapter = adapter

        val editUsername = findViewById<EditText>(R.id.editUsername)
        val buttonQuery = findViewById<Button>(R.id.buttonQuery)

        buttonQuery.setOnClickListener {
            val username = editUsername.text.toString()
            if (username.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val repos = api.listRepos(username)
                        withContext(Dispatchers.Main) {
                            // 리사이클러뷰 어댑터에 새로운 데이터를 설정합니다.
                            adapter.updateData(repos)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        /*recyclerView.adapter = MyAdapter(emptyList())

        CoroutineScope(Dispatchers.IO).launch{
            val repos = api.listRepos("jyheo")
            for(r in repos){
                println("${r.name}")
            }
            withContext(Dispatchers.Main){
                recyclerView.adapter = MyAdapter(repos)
            }
        }*/
    }
}