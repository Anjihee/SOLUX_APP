package com.hanjisu.surround_jisu

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hanjisu.surround_jisu.CommentData
import com.hanjisu.surround_jisu.R

class CommentListAdapter(private val context: Context, private val itemList: List<CommentData>) :
    RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_comments, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = itemList[position]

        // 커스텀 레이아웃의 요소들을 설정합니다.
        holder.userProfile.setImageResource(currentItem.profileId)
        holder.userName.text = currentItem.userName
        holder.commentTime.text = currentItem.time
        holder.comment.text = currentItem.comment
    }

    override fun getItemCount(): Int {
        Log.d("RecyclerView", "itemList size: ${itemList.size}")
        return itemList.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userProfile: ImageView = view.findViewById(R.id.comUserProfile)
        val userName: TextView = view.findViewById(R.id.comUserName)
        val commentTime: TextView = view.findViewById(R.id.commentTime)
        val comment: TextView = view.findViewById(R.id.comment)
    }
}
