package com.seonah.solux_surround_mycommunitylog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seonah.solux_surround_mycommunitylog.databinding.ActivityCommunityLogBinding
import com.seonah.solux_surround_mycommunitylog.databinding.CommunityLogPostsItemBinding
import com.seonah.solux_surround_mycommunitylog.databinding.FragmentCommunityLogPostsBinding
import com.seonah.solux_surround_mycommunitylog.databinding.FragmentHomeBinding
import com.seonah.solux_surround_mycommunitylog.databinding.HomeCommunityItemBinding
import com.seonah.solux_surround_mycommunitylog.databinding.HomeMarektItemBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    //리사이클러뷰
    private lateinit var marketRecyclerView: RecyclerView
    private lateinit var communityRecyclerView: RecyclerView
    private lateinit var adapterForMarket: HomeMarketAdapter
    private lateinit var adapterForCommunity:HomeCommunityAdapter


    override fun onCreateView(
        //뷰가 화면에 그려질 때
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//-------------------------------리사이클러뷰-------------------------
        // Initialize RecyclerView
        marketRecyclerView= binding.marketRecycler
        communityRecyclerView=binding.communityRecycler

        //커뮤니티 리사이클러뷰 레이아웃매니저
        val communityLayoutManager = LinearLayoutManager(requireContext())
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
        val marketLayoutManager=GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false)
        marketRecyclerView.layoutManager=marketLayoutManager
        adapterForMarket.setData(marketData)

        val communityData=getCommunityData()
        adapterForCommunity.setData(communityData)


    }

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
    class HomeMarketAdapter: RecyclerView.Adapter<HomeFragment.HomeMarketViewHolder>(){
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
    class HomeCommunityAdapter: RecyclerView.Adapter<HomeFragment.HomeCommunityViewHolder>(){
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

