package com.hanjisu.surround_jisu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class CommunityPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_post)

        val btnLike: ImageButton = findViewById(R.id.btnLike)

        btnLike.setOnClickListener{
            btnLike.isSelected = !btnLike.isSelected
        }
    }
}