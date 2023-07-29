package com.hanjisu.surround_jisu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class CommentListAdapter(context: Context, private val itemList: List<CommentData>) :
    ArrayAdapter<CommentData>(context, 0, itemList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val holder: ViewHolder

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.listview_comments, parent, false)
            holder = ViewHolder(itemView)
            itemView.tag = holder
        } else {
            holder = itemView.tag as ViewHolder
        }

        val currentItem = itemList[position]

        // 커스텀 레이아웃의 요소들을 설정합니다.
        holder.userProfile.setImageResource(currentItem.profileId)
        holder.userName.text = currentItem.userName
        holder.commentTime.text = currentItem.time
        holder.comment.text = currentItem.comment

        return itemView!!
    }

    private class ViewHolder(view: View) {
        val userProfile: ImageView = view.findViewById(R.id.commentProfile)
        val userName: TextView = view.findViewById(R.id.comUserName)
        val commentTime: TextView = view.findViewById(R.id.commentTime)
        val comment: TextView = view.findViewById(R.id.comment)
    }
}