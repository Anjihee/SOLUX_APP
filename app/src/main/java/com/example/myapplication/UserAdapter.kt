package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class UserAdapter (val context: Context, val UserList: ArrayList<User>) : BaseAdapter()

{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view :View = LayoutInflater.from(context).inflate(R.layout.activity_marketpost, null)

        val profile = view.findViewById<ImageView>(R.id.strawberry)
        val title = view.findViewById<TextView>(R.id.market_title)
        val locate = view.findViewById<TextView>(R.id.market_locate)
        val price = view.findViewById<TextView>(R.id.market_price)

        val user = UserList[position]

        profile.setImageResource(user.profile)
        title.text = user.title
        locate.text = user.locate
        price.text = user.price

        return view


    }


    override fun getItem(position: Int): Any {
        return UserList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return UserList.size
    }



}