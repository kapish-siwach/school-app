package com.first.schoolapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.first.schoolapp.R
import com.first.schoolapp.databases.TeachersDatabse
import com.first.schoolapp.databinding.ActivityMainBinding
import com.first.schoolapp.loginSignup.LoginActivity
import com.first.schoolapp.loginSignup.PreferenceManager
import com.first.schoolapp.repos.TeachersRepository
import com.first.schoolapp.viewmodel.TeachersViewModel
import com.first.schoolapp.viewmodel.TeachersViewModelFactory
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var navigationView: NavigationView
    private lateinit var menuheader: View

    private val teachersViewModel:TeachersViewModel by viewModels {
        val database=TeachersDatabse.invoke(applicationContext)
        val repository= TeachersRepository(database)
        TeachersViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(this)
        navigationView=binding.navigationView



        menuheader=binding.navigationView.getHeaderView(0)
        val showName=menuheader.findViewById<TextView>(R.id.showName)
        val showEmail=menuheader.findViewById<TextView>(R.id.showEmail)
        val showphone=menuheader.findViewById<TextView>(R.id.showphone)


        val email=preferenceManager.getLoggedInEmail()
        if (email != null) {
            teachersViewModel.getTeacherByEmail(email)
        }else{
            Toast.makeText(this, "No email found for logged-in user", Toast.LENGTH_SHORT).show()
        }
        teachersViewModel.teacherData.observe(this, Observer{teacher->
            teacher?.let{
                showName.text=it.name
                showEmail.text=it.email
                showphone.text=it.tphone
            }

        })


        binding.menubtn.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.navigationView)
        }

        binding.signOutBtn.setOnClickListener {
           signOut()
        }
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.contact -> {
                    Snackbar.make(binding.root, "Contact", Snackbar.LENGTH_SHORT).show()
                    true
                }
                R.id.gallery -> {
                    Snackbar.make(binding.root, "Gallery", Snackbar.LENGTH_SHORT).show()
                    true
                }
                R.id.signOut -> {
                   signOut()
                }
                else -> false
            }.also {
                binding.drawerLayout.closeDrawer(binding.navigationView)
            }

            true
        }



    }

    private fun signOut() {
        preferenceManager.clearPreferences()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }
}