package com.seonah.solux_surround_buyingrecord

import android.util.Log

class MarketLogParticipateItem(var title:String, var productImg:String?=null,
                               var time:String, var location:String) {

    val TAG: String = "로그"

    //기본 생성자
    init {
        Log.d(TAG,"MarketLogItemModel -init() called")
    }

}