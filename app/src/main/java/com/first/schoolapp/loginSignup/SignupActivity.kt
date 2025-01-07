package com.first.schoolapp.loginSignup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.first.schoolapp.R
import com.first.schoolapp.activities.MainActivity
import com.first.schoolapp.databases.TeachersDatabse
import com.first.schoolapp.databinding.ActivitySignupBinding
import com.first.schoolapp.entity.TeacherData
import com.first.schoolapp.repos.TeachersRepository
import com.first.schoolapp.viewmodel.TeachersViewModel
import com.first.schoolapp.viewmodel.TeachersViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val repository: TeachersRepository by lazy {
        TeachersRepository(TeachersDatabse.invoke(this))
    }
    private val viewModel: TeachersViewModel by viewModels {
        TeachersViewModelFactory(repository)
    }
    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager= PreferenceManager(this)

        binding.signInLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        binding.signUpButton.setOnClickListener {
            val name = binding.fullNameInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val phone ="91"+ binding.tPhone.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val confirmPassword = binding.confirmPasswordInput.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!email.contains("@")) {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (phone.length != 12||phone.startsWith("+91")) {
                Toast.makeText(this,"Invalid phone number( Don't Use Country Code )", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            GlobalScope.launch {
                val isUnique = viewModel.isEmailUnique(email)
                if (isUnique) {
                    val teacher = TeacherData(name, email, password, phone)
                    viewModel.insert(teacher)
                    runOnUiThread {
                        Toast.makeText(
                            this@SignupActivity,
                            "Account created successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        preferenceManager.setLoggedIn(true)
                        preferenceManager.setLoggedInEmail(email)
                        startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(
                            this@SignupActivity,
                            "Email already exists",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }

        }
    }
}