package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

class MarketHolder : AppCompatActivity() {

    lateinit var profileAdapter: ProfileAdapter
    val datas = mutableListOf<ProfileData>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_marketcomunityholder)

        initRecycler()
    }
    private fun initRecycler() {
        profileAdapter = ProfileAdpter(this)
        rv_profile.adapter = profileAdapter

        datas.apply {
            add()
        }
    }

//    private lateinit var binding: ActivityCommunitypostBinding
//    private lateinit var listAdapter: UserAdapter
//    private lateinit var listData: User
//    var dataArrayList = ArrayList<User?>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityCommunitypostBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_marketcomunityholder)
//
//
//    val imageList = intArrayOf(
//        R.drawable.strawberry,
//        R.drawable.strawberry,
//        R.drawable.strawberry,
//        R.drawable.strawberry
//    )
//
//    val nameList = arrayOf("딸기 사세요", "딸기 사세요", "딸기 사세요", "딸기 사세요")
//    val locateList = arrayOf("이태원동", "이태원동", "이태원동", "이태원동")
//    val priceList = arrayOf("5000원", "5000원", "5000원", "5000원")
//
//    for (i in imageList.indices) {
//        listData = User(imageList[i], nameList[i], locateList[i], priceList[i])
//        dataArrayList.add(listData)
//    }
//    listAdapter = UserAdapter(this@MarketCommunityHolder, dataArrayList)
//    binding.listview.adapter = listAdapter

//
//        val Adapter = UserAdapter(this, UserList)
//        val listview = findViewById<ListView>(R.id.listview)

//        binding.listView.adapter = Adapter
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