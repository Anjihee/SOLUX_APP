package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
//import kotlinx.android.synthetic.main.activity_main.listview

class MarketCommunityHolder : AppCompatActivity() {

    var UserList = arrayListOf<User>(
        User(R.drawable.strawberry, "딸기공구합니다.", "이태원동", "딸기사세요 고등어도 좋아요"),
        User(R.drawable.strawberry, "딸기공구합니다.", "이태원동", "딸기사세요 고등어도 좋아요"),
        User(R.drawable.strawberry, "딸기공구합니다.", "이태원동", "딸기사세요 고등어도 좋아요"),
        User(R.drawable.strawberry, "딸기공구합니다.", "이태원동", "딸기사세요 고등어도 좋아요"),
        User(R.drawable.strawberry, "딸기공구합니다.", "이태원동", "딸기사세요 고등어도 좋아요"),
        User(R.drawable.strawberry, "딸기공구합니다.", "이태원동", "딸기사세요 고등어도 좋아요"),
        User(R.drawable.strawberry, "딸기공구합니다.", "이태원동", "딸기사세요 고등어도 좋아요"),
        User(R.drawable.strawberry, "딸기공구합니다.", "이태원동", "딸기사세요 고등어도 좋아요"),
        User(R.drawable.strawberry, "딸기공구합니다.", "이태원동", "딸기사세요 고등어도 좋아요")
    )

private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketcomunityholder)

        val Adapter = UserAdapter(this, UserList)
//        val listview = findViewById<ListView>(R.id.listview)

        binding.listView.adapter = Adapter
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

//        var item = arrayOf("사과", "바나나")

//        listview.adapter = ArrayAdapter(this, android.R.layout.activity_list_item, item)
//        val navView: BottomNavigationView = binding.navView



//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }
}