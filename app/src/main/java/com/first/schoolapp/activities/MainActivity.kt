package com.first.schoolapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.first.schoolapp.R
import com.first.schoolapp.databases.TeachersDatabse
import com.first.schoolapp.databinding.ActivityMainBinding
import com.first.schoolapp.freagments.HomeFragment
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

    private val teachersViewModel: TeachersViewModel by viewModels {
        val database = TeachersDatabse.invoke(applicationContext)
        val repository = TeachersRepository(database)
        TeachersViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceManager = PreferenceManager(this)
        navigationView = binding.navigationView

        menuheader = binding.navigationView.getHeaderView(0)
        val showName = menuheader.findViewById<TextView>(R.id.showName)
        val showEmail = menuheader.findViewById<TextView>(R.id.showEmail)
        val showPhone = menuheader.findViewById<TextView>(R.id.showphone)

        val email = preferenceManager.getLoggedInEmail()
        if (email != null) {
            teachersViewModel.getTeacherByEmail(email)
        } else {
            Toast.makeText(this, "No email found for logged-in user", Toast.LENGTH_SHORT).show()
        }

        teachersViewModel.teacherData.observe(this, Observer { teacher ->
            teacher?.let {
                showName.text = it.name
                showEmail.text = it.email
                showPhone.text = it.tphone
            }
        })

        setupToolbarButtons()
        setupNavigationDrawer()
        showFragment(HomeFragment(), "HomeFragment")
    }

    private fun setupToolbarButtons() {
        binding.menubtn.setOnClickListener {
            binding.drawerLayout.openDrawer(binding.navigationView)
        }

        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment != null) {
                updateToolbarButtons(currentFragment::class.java.simpleName)
            }
        }
    }

    private fun updateToolbarButtons(fragmentTag: String?) {
        if (fragmentTag == "HomeFragment") {
            binding.menubtn.visibility = View.VISIBLE
            binding.backBtn.visibility = View.GONE
        } else {
            binding.menubtn.visibility = View.GONE
            binding.backBtn.visibility = View.VISIBLE
        }
    }

    private fun setupNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.contact -> {
                    Snackbar.make(binding.root, "Contact", Snackbar.LENGTH_SHORT).show()
                }
                R.id.gallery -> {
                    Snackbar.make(binding.root, "Gallery", Snackbar.LENGTH_SHORT).show()
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

        binding.signOutBtn.setOnClickListener {
            signOut()
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment, tag)
        if (tag != "HomeFragment") {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
        updateToolbarButtons(tag)
    }

    private fun signOut() {
        preferenceManager.clearPreferences()
        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        finish()
    }
}
