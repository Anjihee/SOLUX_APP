package com.seonah.solux_surround_mycommunitylog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seonah.solux_surround_mycommunitylog.databinding.FragmentCommunityLogPostsBinding
import com.seonah.solux_surround_mycommunitylog.databinding.CommunityLogPostsItemBinding


class CommunityLogPostsFragment: Fragment() {

    //뷰바인딩
    private lateinit var binding: FragmentCommunityLogPostsBinding
    //리사이클러뷰
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyPostAdapter


    override fun onCreateView(
        //뷰가 화면에 그려질 때
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentCommunityLogPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager


        // item 사이 밑줄 추가
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        // Initialize Adapter
        adapter = MyPostAdapter()
        recyclerView.adapter = adapter

        // Populate data
        val data = getData() // Replace with your data source
        adapter.setData(data)


    }

    private fun getData(): List<MyPostModel> {
        // Replace with your own implementation to retrieve data for the RecyclerView
        // This method should return a list of YourData objects
        return listOf(
            //게시글 데이터를 담을 배열
            MyPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다","2023.05.24",104,3),
            MyPostModel(null,"저만 월급이 스쳐 지나가요?","2023.05.24",30,5),
            MyPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다","2023.05.24",104,3),
            MyPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다","2023.05.24",104,3),
            MyPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다","2023.05.24",104,3),
            MyPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다","2023.05.24",104,3),
            MyPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다","2023.05.24",104,3),
            MyPostModel("@drawable/cutecute_dog","저희집 강아지 자랑합니다","2023.05.24",104,3),
            // Add more items as needed
        )
    }

    // Replace YourAdapter with your own adapter implementation
    private class MyPostAdapter : RecyclerView.Adapter<MyPostViewHolder>() {
        private val data = mutableListOf<MyPostModel>()

        fun setData(newData: List<MyPostModel>) {
            data.clear()
            data.addAll(newData)
//            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CommunityLogPostsItemBinding.inflate(inflater, parent, false)
            return MyPostViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MyPostViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int = data.size
    }

    // 리사이클러뷰 아이템 모델에 대한 뷰홀더
    private class MyPostViewHolder(private val binding: CommunityLogPostsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MyPostModel) {
            // Bind data to the views in your item layout
            binding.postTitle.text=item.postTitle
            binding.postDate.text=item.postDate
            binding.viewdNum.text=item.viewedNum.toString()
            binding.commentsNum.text=item.commentsNum.toString()

            //이미지가 있는 글이라면
            if (item.postImg != null) {
                //이미지뷰와 실제 이미지 데이터를 묶음
                Glide
                    .with(binding.postImage)
                    .load(item.postImg)
                    .placeholder(R.color.colorGrey)
                    .into(binding.postImage)
            }   else {
                //이미지가 없는 글이라면
                //이미지 숨김
                binding.postImgContainer.visibility=View.GONE
                binding.postImage.visibility=View.GONE

                // postTextContainer의 constraint 재설정
                val layoutParams = binding.postTextContainer.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                binding.postTextContainer.layoutParams = layoutParams

            }
            // deleteBtn에 클릭 리스너 추가
            binding.deleteBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // 데이터 리스트에서 아이템 제거
                    data.removeAt(position)
                    // 어댑터에 데이터 변경을 알림
                    notifyItemRemoved(position)
                }
            }
        }
        }


}

// 게시글 데이터 모델
private data class MyPostModel(var postImg:String?=null, var postTitle:String?=null, var postDate:String?=null,
                                   var viewedNum:Int?=null, var commentsNum:Int?=null) {


    val TAG:String="로그"

    init {
            Log.d(TAG, "MyPostModel - init() called")
    }

}



