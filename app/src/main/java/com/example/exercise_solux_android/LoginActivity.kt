package com.example.exercise_solux_android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.exercise_solux_android.databinding.ActivityLoginBinding

class LoginActivity : ComponentActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        val btnJoin = findViewById<Button>(R.id.btnJoin) //계정만들기 버튼
        val intent = Intent(this, JoinActivity::class.java) //가입 화면

        btnJoin.setOnClickListener {
            startActivity(intent)
        }
    }
}

