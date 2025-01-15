package com.first.schoolapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.first.schoolapp.databinding.ActivitySplashBinding
import com.first.schoolapp.loginSignup.PreferenceManager
import com.first.schoolapp.loginSignup.SignupActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)

        // Splash delay and redirection
        lifecycleScope.launch {
            delay(500)
            val nextActivity = if (preferenceManager.isLoggedIn()) MainActivity::class.java else SignupActivity::class.java
            startActivity(Intent(this@SplashActivity, nextActivity))
            finish()
        }
    }
}
