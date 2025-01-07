package com.first.schoolapp.loginSignup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.first.schoolapp.activities.MainActivity
import com.first.schoolapp.databases.TeachersDatabse
import com.first.schoolapp.databinding.ActivityLoginBinding
import com.first.schoolapp.repos.TeachersRepository
import com.first.schoolapp.viewmodel.TeachersViewModel
import com.first.schoolapp.viewmodel.TeachersViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferenceManager:PreferenceManager
    private val repository:TeachersRepository by lazy {
        TeachersRepository(TeachersDatabse.invoke(this))
    }
    private val viewModel:TeachersViewModel by viewModels {
        TeachersViewModelFactory(repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        preferenceManager=PreferenceManager(this)

        binding.signUpLink.setOnClickListener {
            startActivity(Intent(this@LoginActivity,SignupActivity::class.java))
        }

        binding.loginButton.setOnClickListener {
            val email=binding.emailInput.text.toString().trim()
            val password=binding.passwordInput.text.toString().trim()

            if (email.isEmpty()||password.isEmpty()){
                Toast.makeText(this,"All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GlobalScope.launch {
                val teacher=viewModel.validateTeacher(email,password)
                withContext(Dispatchers.Main){
                    if (teacher != null) {
                        Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                        preferenceManager.setLoggedIn(true)
                        preferenceManager.setLoggedInEmail(email)
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}