package com.seonah.surround2023

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.seonah.surround2023.databinding.ActivitySplashScreenBinding


class SplashScreenActivity: Activity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        splashAnimation()

        setContentView(binding.root)



    }

    private fun splashAnimation(){
        val animationImage = AnimationUtils.loadAnimation(this, R.anim.anim_splash_image)
        val animationText = AnimationUtils.loadAnimation(this, R.anim.anim_splash_text)
        binding.logoIcon.startAnimation(animationImage)
        binding.logoText.startAnimation(animationText)

        animationText.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                startMainActivity()
            }
        })

    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Optional: If you want to finish the splash screen activity when starting the main activity
    }


}