package com.first.schoolapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.first.schoolapp.R
import com.first.schoolapp.databinding.ActivitySplashBinding
import com.first.schoolapp.loginSignup.PreferenceManager
import com.first.schoolapp.loginSignup.SignupActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager=PreferenceManager(this)

        Handler().postDelayed({
            val signupIntent=Intent(this, SignupActivity::class.java)
            val mainIntent=Intent(this, MainActivity::class.java)
            if (preferenceManager.isLoggedIn()){
                startActivity(mainIntent)
            }else{
                startActivity(signupIntent)
            }
            finish()
        },500)
    }
}