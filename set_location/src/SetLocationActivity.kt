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
import androidx.core.content.ContentProviderCompat.requireContext
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
    // 위치 권한 요청에 사용할 상수 정의
    private val PERMISSION_REQUEST_CODE = 100
    private val LOCATION_SETTINGS_REQUEST_CODE = 101


    //searchView를 위한 상수
    companion object {
        private const val SEARCH_ADDRESS_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySetLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //        -------------------- 사용자 위치 찾기 ------------------
        fusedLocationClient=LocationServices.getFusedLocationProviderClient(this)
        geocoder= Geocoder(this, Locale.KOREAN)
        // 위치 권한 확인 및 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_REQUEST_CODE
            )
        } else {
            // 위치 권한이 이미 허용된 경우, 위치를 가져오는 메서드를 호출합니다.
            retrieveLocation()
        }



            //검색창 이벤트
            searchView = binding.searchView
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // 사용자가 검색 버튼을 눌렀을 때 호출되는 콜백
//                if (!query.isNullOrEmpty()) {
//                    // 검색어가 null 또는 빈 문자열이 아니라면 검색어를 searchAddressActivity로 전달
//                    val intent = Intent(this@SetLocationActivity, SearchAddressActivity::class.java)
//                    intent.putExtra("SEARCH_QUERY", query)
//                    startActivityForResult(intent, SEARCH_ADDRESS_REQUEST_CODE)
//                }
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // 사용자가 검색어를 입력할 때마다 호출되는 콜백 (여기서는 처리하지 않음)
//                return true
//            }
//        })


            //뒤로가기 버튼 이벤트
            binding.prevPageBtn.setOnClickListener {
                if (searchView.hasFocus()) {
                    //검색창이 활성화 되어 있을 땐 검색창을 초기화하고
                    searchView.setQuery("", false)
                    //키보드를 숨김
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(searchView.windowToken, 0)

                    searchView.clearFocus()
                    Log.d(TAG, "searchView Focus : ${searchView.hasFocus()}")

                } else {
                    // 검색창이 비활성화 되어 있을시엔
                    // SetLocationActivity를 종료하여 이전 화면으로 돌아감
                    Log.d(TAG, "setLocationActivity is finished")
                    finish()
                }

            }


            // saveLocationBtn 클릭 이벤트 처리
            val saveLocationBtn = binding.setUserLocationBtn
            saveLocationBtn.setOnClickListener {
                // 현재 위치 주소를 HomeFragment에 전달
                val currentStreetAddress = binding.userLocationText.text.toString()
                val intent = Intent(this@SetLocationActivity, HomeActivity::class.java)
                intent.putExtra("CURRENT_ADDRESS", currentStreetAddress)
                setResult(Activity.RESULT_OK, intent)
                // SetLocationActivity를 종료하고, onBackPressed()를 호출하여 결과 전달
                finish()
            }

    }

    private fun retrieveLocation() {
        var currentLocation:List<Address>?
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val lastLocation: Location? = locationResult.lastLocation
                    if (lastLocation != null) {
                        val latitude = lastLocation.latitude
                        val longitude = lastLocation.longitude
                        currentLocation= geocoder.getFromLocation(latitude,longitude,1)
                        binding.userLocationText.text= currentLocation?.get(0)?.thoroughfare
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }
    }

    //위치 권한 요청 결과를 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 위치 권한이 허용된 경우, 위치를 가져오는 메서드를 호출합니다.
                retrieveLocation()
            } else {
                // 위치 권한이 거부된 경우에 대한 처리를 하세요. (예: 메시지를 표시하거나 아무 작업도 하지 않음)
            }
        }
    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LOCATION_SETTINGS_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // 사용자가 위치 설정을 변경하여 만족시킨 경우, 위치를 가져오는 메서드를 호출합니다.
                retrieveLocation()
            } else {
                // 사용자가 위치 설정 변경을 거부한 경우에 대한 처리를 하세요. (예: 메시지를 표시하거나 아무 작업도 하지 않음)
            }
        }

        if (requestCode == SEARCH_ADDRESS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                // Get the submitted search query from the result intent
                val submittedQuery = data?.getStringExtra("SEARCH_QUERY_RESULT")
                if (!submittedQuery.isNullOrEmpty()) {
                    // Update the userLocationText with the submitted search query
                    binding.locationLabelTextView.text = "검색한 위치"
                    binding.userLocationText.text = submittedQuery
                }
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