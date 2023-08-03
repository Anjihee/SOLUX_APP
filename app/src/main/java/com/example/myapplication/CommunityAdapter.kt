package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import org.techtown.location.Myproject3.databinding.ItemRecyclerBinding
import androidx.recyclerview.widget.RecyclerView

class CommunityAdapter : RecyclerView.Adapter<Holder>() {
    var listData = mutableListOf<Community>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val community = listData.get(position)
        holder.setCommunity(community)
    }
}

class Holder(val binding: ItemRecylcerBinding) : RecyclerView.ViewHolder(binding.root) {
    init{
        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context, "클릭된 아이템 = ${binding.textTitle.text}",
                            Toast.LENGTH_LONG).show()
        }
    }

    fun setCommunity(community: Community) {
        binding.picture.int = community.picture
        binding.category.text = community.category
        binding.title.text = community.title
        binding.detail.text = community.detail
        binding.comment.text = community.comment
    }
}