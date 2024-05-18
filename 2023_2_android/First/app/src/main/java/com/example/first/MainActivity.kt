package com.example.first

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // NavigationBarView의 setOnItemSelectedListener를 사용하여 메뉴 아이템 선택 이벤트 처리
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // "home" 메뉴가 선택된 경우
                    val intent = Intent(this@MainActivity, MainActivity2::class.java)
                    startActivity(intent)
                    true
                }
                R.id.chatList -> {
                    // "chatList" 메뉴가 선택된 경우
                    // chatList에 대한 추가 동작을 정의할 수 있음
                    true
                }
                R.id.myPage -> {
                    // "myPage" 메뉴가 선택된 경우
                    // myPage에 대한 추가 동작을 정의할 수 있음
                    true
                }
                else -> false
            }
        }

        // 초기 선택 메뉴 설정
        bottomNavigationView.selectedItemId = R.id.chatList
    }
}
