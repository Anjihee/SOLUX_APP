package com.seonah.solux_surround_mycommunitylog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seonah.solux_surround_mycommunitylog.databinding.FragmentCommunityLogCommentsBinding
import com.seonah.solux_surround_mycommunitylog.databinding.CommunityLogCommentsItemBinding

class CommunityLogCommentsFragment: Fragment() {


    //뷰바인딩
    private lateinit var binding: FragmentCommunityLogCommentsBinding

    //리사이클러뷰
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CommunityLogCommentsFragment.MyCommentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityLogCommentsBinding.inflate(inflater, container, false)
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
        adapter = CommunityLogCommentsFragment.MyCommentAdapter()
        recyclerView.adapter = adapter

        // Populate data
        val data = getData() // Replace with your data source
        adapter.setData(data)


    }

    private fun getData(): List<MyCommentModel> {
        // Replace with your own implementation to retrieve data for the RecyclerView
        // This method should return a list of YourData objects
        return listOf(
            //댓글 데이터를 담을 배열
            MyCommentModel("저는 낭비 아티스트예요ㅎㅎ", "다들 저축 잘 하고 계신가요?", "2023.05.24"),
            MyCommentModel("교수님께 메일 보내시는 거 추천", "대학생분들 도와주세요", "2023.03.10"),
            MyCommentModel("조명ㅈㅂㅈㅇ", "인테리어 바꿔봤어요", "2023.05.24"),
            MyCommentModel("저는 낭비 아티스트예요ㅎㅎ", "다들 저축 잘 하고 계신가요?", "2023.07.18"),
            MyCommentModel("저는 낭비 아티스트예요ㅎㅎ", "다들 저축 잘 하고 계신가요?", "2023.05.24"),
            MyCommentModel("저는 낭비 아티스트예요ㅎㅎ", "다들 저축 잘 하고 계신가요?", "2023.05.24"),
            // Add more items as needed
        )
    }

    // Replace YourAdapter with your own adapter implementation
    private class MyCommentAdapter : RecyclerView.Adapter<CommunityLogCommentsFragment.MyCommentViewHolder>() {
        private val data = mutableListOf<MyCommentModel>()

        fun setData(newData: List<MyCommentModel>) {
            data.clear()
            data.addAll(newData)
//            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CommunityLogCommentsFragment.MyCommentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CommunityLogCommentsItemBinding.inflate(inflater, parent, false)
            return CommunityLogCommentsFragment.MyCommentViewHolder(binding)
        }

        override fun onBindViewHolder(holder: CommunityLogCommentsFragment.MyCommentViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int = data.size
    }

    // Replace YourViewHolder with your own ViewHolder implementation
    private class MyCommentViewHolder(private val binding: CommunityLogCommentsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MyCommentModel) {
            // Bind data to the views in your item layout
            binding.commentContent.text = item.commentContent
            binding.commentedPostTitle.text = item.commentedPostTitle
            binding.commentDate.text = item.commentDate

        }

    }

    // 댓글 데이터 모델
    private data class MyCommentModel(
        var commentContent: String? = null,
        var commentedPostTitle: String? = null,
        var commentDate: String? = null, ) {

        val TAG: String = "로그"

        init {
            Log.d(TAG, "MyPostModel - init() called")
        }
    }
}