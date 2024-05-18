package com.example.myapplication10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        setupActionBarWithNavController(navController,appBarConfiguration)

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean { //frag1,frag2에서 화살표 누르면 Home으로 되돌아가는 코드
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        //return super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.frag1, R.id.frag2 ->
                item.onNavDestinationSelected(findNavController(R.id.fragmentContainerView))
            else -> return super.onOptionsItemSelected(item)
        }
        /*when(item.title) {

            "Item" -> println("item 선택")
            "Item2" -> println("item1 선택")
            "Item3" -> println("item2 선택")
            -------------------------------------
            R.id.home2 -> MyDialogFragments().show(supportFragmentManager,"MyDialog")
            R.id.frag1 -> println("item2 선택")
            R.id.frag2 -> println("item3 선택")

            else -> return super.onOptionsItemSelected(item)
        }*/
        return true
    }
}