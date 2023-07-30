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
import android.view.inputmethod.InputMethodManager
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

        //뒤로가기 버튼 이벤트
        binding.prevPageBtn.setOnClickListener{
            if(searchView.hasFocus()){
                //검색창이 활성화 되어 있을 땐 검색창을 초기화하고
                searchView.setQuery("", false)
                //키보드를 숨김
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(searchView.windowToken, 0)

                searchView.clearFocus()
                Log.d(TAG, "searchView Focus : ${searchView.hasFocus()}")

            }
            else{
                // 검색창이 비활성화 되어 있을시엔
                // SetLocationActivity를 종료하여 이전 화면으로 돌아감
                Log.d(TAG, "setLocationActivity is finished")
                finish()
            }

        }

//        -------------------- 사용자 위치 찾기 ------------------
        // Initialize fusedLocationClient and geocoder
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        geocoder = Geocoder(this, Locale.KOREAN)

// Get user's location and update the UI
        getUserLocation(object : LocationCallback {
            override fun onLocationReceived(currentStreetAddress: String?) {
                binding.userLocationText.text = currentStreetAddress
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
        // Check for location permission
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                10101
            )
        }

        // Get last known location
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // If the location is not null, convert it to an address
                if (location != null) {
                    val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (addressList!!.isNotEmpty()) {
                        val currentStreetAddress = addressList?.get(0)?.thoroughfare
                        callback.onLocationReceived(currentStreetAddress)

                    }
                } else {
                    callback.onLocationReceived(null)
                }
            }
            .addOnFailureListener {
                callback.onLocationReceived(null)
            }
    }



    //homeFragment로 돌아감
    override fun onBackPressed() {
        // HomeFragment로 돌아가기 위해 현재 설정된 위치 주소를 가져옴
        val currentStreetAddress = binding.userLocationText.text.toString()
        val intent = Intent()
        //Intent에 현재 설정된 위치 주소를 넣음
        intent.putExtra("CURRENT_ADDRESS", currentStreetAddress)
        setResult(Activity.RESULT_OK, intent)
        // SetLocationActivity를 종료하여 이전 화면으로 돌아감
        super.onBackPressed()
    }



}