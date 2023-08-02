package com.hanjisu.surround_jisu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class JoinMarketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_market)
        
        val btnBack : ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener { // '뒤로가기' 버튼 클릭 시
            // '뒤로가기' 버튼을 클릭하면 공구 글 페이지를 열도록 인텐트 생성
            val intent = Intent(this@JoinMarketActivity, MarketPostActivity::class.java)
            startActivity(intent)
        }

        val radioGroup : RadioGroup = findViewById(R.id.radioGroup1)
        val payCard : RadioButton = findViewById(R.id.payCard)
        val payNaver : RadioButton = findViewById(R.id.payNaver)
        val payKakao : RadioButton = findViewById(R.id.payKakao)
        val payPhone : RadioButton = findViewById(R.id.payPhone)
        val payDeposit : RadioButton = findViewById(R.id.payDeposit)

        radioGroup.setOnCheckedChangeListener { group, checkedId -> // 결제방법 선택 시
            if (checkedId == R.id.payCard) { // 카드로 결제 선택
                payCard.setBackgroundResource(R.drawable.selected_roundsquare)
                payNaver.setBackgroundResource(R.drawable.roundsquare)
                payKakao.setBackgroundResource(R.drawable.roundsquare)
                payPhone.setBackgroundResource(R.drawable.roundsquare)
                payDeposit.setBackgroundResource(R.drawable.roundsquare)
            } else if (checkedId == R.id.payNaver) { // 네이버페이 결제 선택
                payCard.setBackgroundResource(R.drawable.roundsquare)
                payNaver.setBackgroundResource(R.drawable.selected_roundsquare)
                payKakao.setBackgroundResource(R.drawable.roundsquare)
                payPhone.setBackgroundResource(R.drawable.roundsquare)
                payDeposit.setBackgroundResource(R.drawable.roundsquare)
            } else if (checkedId == R.id.payKakao) { // 카카오페이 결제 선택
                payCard.setBackgroundResource(R.drawable.roundsquare)
                payNaver.setBackgroundResource(R.drawable.roundsquare)
                payKakao.setBackgroundResource(R.drawable.selected_roundsquare)
                payPhone.setBackgroundResource(R.drawable.roundsquare)
                payDeposit.setBackgroundResource(R.drawable.roundsquare)
            } else if (checkedId == R.id.payPhone) { // 휴대폰 결제 선택
                payCard.setBackgroundResource(R.drawable.roundsquare)
                payNaver.setBackgroundResource(R.drawable.roundsquare)
                payKakao.setBackgroundResource(R.drawable.roundsquare)
                payPhone.setBackgroundResource(R.drawable.selected_roundsquare)
                payDeposit.setBackgroundResource(R.drawable.roundsquare)
            } else if (checkedId == R.id.payDeposit) { // 무통장입금 결제 선택
                payCard.setBackgroundResource(R.drawable.roundsquare)
                payNaver.setBackgroundResource(R.drawable.roundsquare)
                payKakao.setBackgroundResource(R.drawable.roundsquare)
                payPhone.setBackgroundResource(R.drawable.roundsquare)
                payDeposit.setBackgroundResource(R.drawable.selected_roundsquare)
            } else { // 아무것도 선택 안됨
                payCard.setBackgroundResource(R.drawable.roundsquare)
                payNaver.setBackgroundResource(R.drawable.roundsquare)
                payKakao.setBackgroundResource(R.drawable.roundsquare)
                payPhone.setBackgroundResource(R.drawable.roundsquare)
                payDeposit.setBackgroundResource(R.drawable.roundsquare)
            }
        }
    }
}