package com.seonah.solux_surround_mycommunitylog.set_location

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoAPI {
    @GET("v2/local/search/address.json")    // Address.json의 정보를 받아옴
    fun getSearcAddress(
        @Header("Authorization") key: String,     // 카카오 API 인증키 [필수]
        @Query("query") query: String,     // 검색을 원하는 질의어 [필수]
    ):Call<ResultSearchKeyword> // 받아온 정보가 ResultSearchKeyword 클래스의 구조로 담김

}