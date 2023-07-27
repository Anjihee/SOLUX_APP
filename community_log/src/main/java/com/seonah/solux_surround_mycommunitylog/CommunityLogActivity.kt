package com.seonah.solux_surround_mycommunitylog

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.seonah.solux_surround_mycommunitylog.databinding.ActivityCommunityLogBinding

class CommunityLogActivity : AppCompatActivity() {
    private val TAG = "로그"

    private lateinit var binding: ActivityCommunityLogBinding

    //하단 Nav 와 관련된 변수
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var bottomMenuContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //뷰바인딩
        binding = ActivityCommunityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "MainActivity - onCreate() called")

//        ------------------게시글/댓글 탭에 대한 기능 구현 ---------------------------
        val viewPager: ViewPager2 = binding.viewPager

        val tabLayout: TabLayout = binding.tabLayout
        Log.d(TAG, "CommunityLogActivity - viewBinding: viewPager, tabLayout")

        // 어댑터 설정
        val viewPagerFragmentAdapter = CommunityLogViewPager2FragmentAdapter(this)
        viewPager.adapter = viewPagerFragmentAdapter

        // tabLayout, viewPager 연결
        val tabTitles = listOf("게시글", "댓글")
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()



//        ---------------------BottomNavigationView에 대한 기능 ---------------
        bottomNavView = binding.bottomNavView
        setListeners()
        //맨 처음 시작할 탭 설정
//        bottomNavView.selectedItemId = R.id.menu_home
        bottomMenuContainer = findViewById(R.id.bottomMenuContainer)
        hideBottomMenuContainer()


        //------ 하단 네비게이션 기본 소프트키 안보이게--------------------------
        val uiOptions = window.decorView.systemUiVisibility
        var newUiOptions = uiOptions
        val isImmersiveModeEnabled = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions
        if (isImmersiveModeEnabled) {
            Log.i("Is on?", "Turning immersive mode mode off. ")
        } else {
            Log.i("Is on?", "Turning immersive mode mode on.")
        }
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = newUiOptions

    }

    private fun setListeners() {
        //선택 리스너 등록
        bottomNavView.setOnNavigationItemSelectedListener(TabSelectedListener())
    }

    inner class TabSelectedListener : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

            when (menuItem.itemId) {
                R.id.menu_home, R.id.menu_favorite, R.id.menu_addPost, R.id.menu_chat, R.id.menu_mypage -> {
                    showBottomMenuContainer()
                    showFragment(menuItem.itemId) // 해당 Fragment 표시
                    hideTabLayoutAndViewPager() // tabLayout과 ViewPager 숨김
                    return true
                }
                // 다른 탭에 대한 처리...
                else -> return false
            }
        }

        private fun showFragment(itemId: Int) {
            val fragment: Fragment = when (itemId) {
                R.id.menu_home -> HomeFragment()
                R.id.menu_favorite -> FavoriteFragment()
                R.id.menu_addPost -> AddPostFragment()
                R.id.menu_chat -> ChatFragment()
                R.id.menu_mypage -> MypageFragment()
                else -> HomeFragment() // 기본적으로 HomeFragment를 표시
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.bottomMenuContainer, fragment)
                .commit()

        }

        private fun hideTabLayoutAndViewPager() {
            binding.tabLayout.visibility = View.GONE
            binding.viewPager.visibility = View.GONE
        }

    }

    private fun hideBottomMenuContainer() {
        bottomMenuContainer.visibility = View.GONE
    }

    private fun showBottomMenuContainer() {
        bottomMenuContainer.visibility = View.VISIBLE
    }


}

