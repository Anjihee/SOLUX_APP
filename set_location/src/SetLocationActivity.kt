package com.seonah.solux_surround_mycommunitylog

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationRequest
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.seonah.solux_surround_mycommunitylog.databinding.ActivitySetLocationBinding
import java.io.IOException
import java.util.*

class SetLocationActivity : AppCompatActivity() {
    var TAG:String="로그"

    //뷰바인딩
    private lateinit var binding:ActivitySetLocationBinding
    private lateinit var searchView: SearchView

    //geocode를 위한 변수
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var geocoder:Geocoder



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySetLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchView = binding.searchView
        searchView.setIconifiedByDefault(false)
        searchView.queryHint = "동명 (예: 청파동)으로 검색하기"

//        -------------------- 사용자 위치 찾기 ------------------
        getUserLocation(object : LocationCallback {
            override fun onLocationReceived(currentStreetAddress: String?) {
                binding.userLocationText.text=currentStreetAddress
            }
        })

        // saveLocationBtn 클릭 이벤트 처리
        val saveLocationBtn=binding.setUserLocationBtn
        saveLocationBtn.setOnClickListener {
            // 현재 위치 주소를 HomeFragment에 전달
            val currentStreetAddress = binding.userLocationText.text.toString()
            val intent = Intent(this@SetLocationActivity, HomeFragment::class.java)
            intent.putExtra("CURRENT_ADDRESS", currentStreetAddress)
            setResult(Activity.RESULT_OK, intent)
            // SetLocationActivity를 종료하고, onBackPressed()를 호출하여 결과 전달
            finish()
        }

    }

    // LocationCallback 인터페이스 정의
    interface LocationCallback {
        fun onLocationReceived(currentStreetAddress: String?)
    }


    private fun getUserLocation(callback: LocationCallback) {

        // FusedLocationProviderClient 초기화
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        //Geocoder 초기화
        geocoder=Geocoder(this,Locale.KOREAN)

        //위치 권한이 허용되었는지 확인하고, 허용되지 않았다면 사용자에게 요청
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),10101)
        }

        val lastLocation=fusedLocationClient.lastLocation
        //위치 정보 가져오기를 성공했을 떄
        lastLocation.addOnSuccessListener {
            Log.d(TAG, "getLastLocation: Latitude ${it.latitude}, Longitude: ${it.longitude}")

            val address=geocoder.getFromLocation(it.latitude,it.longitude,1)
            val currentStreetAddress=address?.get(0)?.thoroughfare

            Log.d(TAG,"getLastLocation:${address?.get(0)?.getAddressLine(0)}")
            Log.d(TAG,"getLastLocation:${currentStreetAddress}")

            binding.userLocationText.text=currentStreetAddress

            callback.onLocationReceived(currentStreetAddress)

        }

        //실패했을 때
        lastLocation.addOnFailureListener{
            Log.d(TAG,"getLastLocation:Failed to load")
            callback.onLocationReceived(null)
        }


    }

    //homeFragment로 돌아감
    override fun onBackPressed() {
        val currentStreetAddress = binding.userLocationText.text.toString()
        val intent = Intent()
        intent.putExtra("CURRENT_ADDRESS", currentStreetAddress)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }



}