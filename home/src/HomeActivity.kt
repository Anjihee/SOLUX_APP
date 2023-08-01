package com.seonah.solux_surround_mycommunitylog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.seonah.solux_surround_mycommunitylog.databinding.ActivityHomeBinding

import com.seonah.solux_surround_mycommunitylog.databinding.HomeCommunityItemBinding
import com.seonah.solux_surround_mycommunitylog.databinding.HomeMarektItemBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    //하단 Nav 와 관련된 변수
    private lateinit var bottomNavView: BottomNavigationView

    //리사이클러뷰
    private lateinit var marketRecyclerView: RecyclerView
    private lateinit var communityRecyclerView: RecyclerView
    private lateinit var adapterForMarket: HomeMarketAdapter
    private lateinit var adapterForCommunity:HomeCommunityAdapter


    //설정된 사용자 위치를 가져오기 위한 상수
    private val LOCATION_REQUEST_CODE = 1001


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 소프트키(네비게이션 바), 상태바를 숨기기 위한 플래그 설정
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        //        ---------------------BottomNavigationView에 대한 기능 ---------------
        bottomNavView = binding.bottomNavView
        bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    // "menu_home" 아이템 클릭 시 HomeActivity로 이동
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                // 다른 메뉴 아이템에 대한 처리 추가 (필요에 따라 다른 Activity로 이동할 수 있음)
                else -> false
            }
        }

        //-------------------------------리사이클러뷰-------------------------
        // Initialize RecyclerView
        marketRecyclerView= binding.marketRecycler
        communityRecyclerView=binding.communityRecycler

        //커뮤니티 리사이클러뷰 레이아웃매니저
        val communityLayoutManager = LinearLayoutManager(this)
        communityRecyclerView.layoutManager = communityLayoutManager


        // Initialize Adapter
        adapterForMarket = HomeMarketAdapter()
        adapterForCommunity = HomeCommunityAdapter()
        marketRecyclerView.adapter = adapterForMarket
        communityRecyclerView.adapter=adapterForCommunity

        // Add item decoration to the marketRecyclerView
        val itemSpace = resources.getDimensionPixelSize(R.dimen.home_market_item_space) // 이 부분에서 R.dimen.item_space는 간격 크기를 리소스로 정의한 값입니다.
        marketRecyclerView.addItemDecoration(HomeMarketItemDecoration(itemSpace, 2))

        // Populate data
        val marketData = getMarketData() // Replace with your data source
        //공동구매 리사이클러뷰 레이아웃매니저
        val marketLayoutManager=GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false)
        marketRecyclerView.layoutManager=marketLayoutManager
        adapterForMarket.setData(marketData)

        val communityData=getCommunityData()
        adapterForCommunity.setData(communityData)



//        ---------------------사용자 위치 설정 기능 -------------------------------
        binding.userLocationArea.setOnClickListener{
            showDropdownMenu(it)
        }


    }









    //검색 동작 처리를 위한 메소드
//    private fun handleSearchAction(searchText:String){
//
//        val intent = Intent(requireContext(), MarketActivity::class.java)
//        //MarketPostActivity로 검색 텍스트를 전달
//        intent.putExtra("searchText", searchText)
//        startActivity(intent)
//    }

    //사용자 위치 Dropdown 메뉴를 보여주는 함수
    private fun showDropdownMenu(view:View){

        val popupMenu = PopupMenu(this,view)
        popupMenu.menuInflater.inflate(R.menu.location_dropdown_menu,popupMenu.menu) //menu layout

        // Dropdown 메뉴의 아이템 클릭 이벤트 처리
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.setUserLocation -> {
                    goSetLocationPage()
                    true
                }

                else -> false
            }
        }
        //Dropdown 메뉴가 보여질 위치 조절
//        popupMenu.gravity=Gravity.START
        popupMenu.show()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.setUserLocation -> {
                // 하단 소프트키 숨기기
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

                return true
            }
            // 다른 메뉴 항목에 대한 처리
            else -> return super.onOptionsItemSelected(item)
        }
    }

    //사용자 위치 설정 페이지로 이동하기 위한 메소드
    fun goSetLocationPage(){
        val intent = Intent(this, SetLocationActivity::class.java)
        startActivityForResult(intent, LOCATION_REQUEST_CODE)// 위치 설정 액티비티 호출
    }

    //사용자 위치 설정 페이지에서 설정된 위치를 home에도 적용

    // 위치 설정 액티비티로부터 결과를 받아오는 콜백 메소드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LOCATION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val currentStreetAddress = data?.getStringExtra("CURRENT_ADDRESS")
            if (!currentStreetAddress.isNullOrEmpty()) {
                // SetLocationActivity로부터 전달받은 주소를 화면에 업데이트
                binding.userLocationText.text = currentStreetAddress
            }
        }
    }

    //공동구매 게시글 데이터를 가져오기 위한 메소드
    private fun getMarketData(): List<MarektPostModel> {
        // Replace with your own implementation to retrieve data for the RecyclerView
        // This method should return a list of YourData objects
        return listOf(
            //데이터를 담을 배열
            MarektPostModel("@drawable/strawberry","딸기 1키로 공구하실 분 구합니다"),
            MarektPostModel("@drawable/strawberry","딸기 1키로 공구하실 분 구합니다"),
            MarektPostModel("@drawable/strawberry","딸기 1키로 공구하실 분 구합니다"),
            MarektPostModel("@drawable/strawberry","딸기 1키로 공구하실 분 구합니다"),
            MarektPostModel("@drawable/strawberry","딸기 1키로 공구하실 분 구합니다"),
            MarektPostModel("@drawable/strawberry","딸기 1키로 공구하실 분 구합니다"),
            MarektPostModel("@drawable/strawberry","딸기 1키로 공구하실 분 구합니다"),
            MarektPostModel("@drawable/strawberry","딸기 1키로 공구하실 분 구합니다"),
            MarektPostModel("@drawable/strawberry","딸기 1키로 공구하실 분 구합니다"),
            MarektPostModel("@drawable/strawberry","딸기 1키로 공구하실 분 구합니다"),

            // Add more items as needed
        )
    }

    //커뮤니티 게시글 데이터를 가져오기 위한 메소드
    private fun getCommunityData(): List<CommunityPostModel> {
        // Replace with your own implementation to retrieve data for the RecyclerView
        // This method should return a list of YourData objects
        return listOf(
            //게시글 데이터를 담을 배열
            CommunityPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다"),
            CommunityPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다"),
            CommunityPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다"),
            CommunityPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다"),
            CommunityPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다"),
            CommunityPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다"),

            // Add more items as needed
        )
    }

    //공동구매 리사이클러뷰 어댑터
    class HomeMarketAdapter: RecyclerView.Adapter<HomeActivity.HomeMarketViewHolder>(){
        private val data = mutableListOf<MarektPostModel>()

        fun setData(newData: List<MarektPostModel>) {
            data.clear()
            data.addAll(newData)
//            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMarketViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = HomeMarektItemBinding.inflate(inflater, parent, false)
            return HomeMarketViewHolder(binding)
        }

        override fun onBindViewHolder(holder: HomeMarketViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int = data.size
    }

    // 공동구매 리사이클러뷰 아이템 모델에 대한 뷰홀더
    class HomeMarketViewHolder(private val binding: HomeMarektItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MarektPostModel) {
            // Bind data to the views in your item layout
            binding.productTitle.text=item.productTitle


            //이미지가 있는 글이라면
            if (item.productImg != null) {
                //이미지뷰와 실제 이미지 데이터를 묶음
                Glide
                    .with(binding.productImg)
                    .load(item.productImg)
                    .placeholder(R.color.colorGrey)
                    .into(binding.productImg)
            }   else {
                //이미지가 없는 글이라면
                //이미지 숨김
                binding.productImgContainer.visibility=View.GONE
                binding.productImg.visibility=View.GONE

                // TextView의 constraint 재설정
                val layoutParams = binding.productTitle.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                binding.productTitle.layoutParams = layoutParams

            }

        }
    }

    //커뮤니티 리사이클러뷰 어댑터
    class HomeCommunityAdapter: RecyclerView.Adapter<HomeActivity.HomeCommunityViewHolder>(){
        private val data = mutableListOf<CommunityPostModel>()

        fun setData(newData: List<CommunityPostModel>) {
            data.clear()
            data.addAll(newData)
//            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCommunityViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = HomeCommunityItemBinding.inflate(inflater, parent, false)
            return HomeCommunityViewHolder(binding)
        }

        override fun onBindViewHolder(holder: HomeCommunityViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int = data.size
    }

    // 커뮤니티 리사이클러뷰 아이템 모델에 대한 뷰홀더
    class HomeCommunityViewHolder(private val binding: HomeCommunityItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommunityPostModel) {
            // Bind data to the views in your item layout
            binding.postTitle.text=item.postTitle


            //이미지가 있는 글이라면
            if (item.postImg != null) {
                //이미지뷰와 실제 이미지 데이터를 묶음
                Glide
                    .with(binding.postImg)
                    .load(item.postImg)
                    .placeholder(R.color.colorGrey)
                    .into(binding.postImg)
            }   else {
                //이미지가 없는 글이라면
                //이미지 숨김
                binding.postImgContainer.visibility=View.GONE
                binding.postImg.visibility=View.GONE

                // TextView의 constraint 재설정
                val layoutParams = binding.postTitle.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                binding.postTitle.layoutParams = layoutParams

            }

        }
    }


}


// 공동구매 게시글 데이터 모델
data class MarektPostModel(var productImg:String?=null, var productTitle:String?=null) {


    val TAG:String="로그"

    init {
        Log.d(TAG, "MarektPostModel - init() called")
    }

}



// 커뮤니티 게시글 데이터 모델
data class CommunityPostModel(var postImg:String?=null, var postTitle:String?=null) {


    val TAG:String="로그"

    init {
        Log.d(TAG, "MarektPostModel - init() called")
    }

}

