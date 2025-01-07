package com.first.schoolapp.loginSignup

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context:Context) {
    private val preferences:SharedPreferences=context.getSharedPreferences("app_preference",Context.MODE_PRIVATE)

    companion object {
        private const val IS_LOGGED_IN = "isLoggedIn"
        private const val LOGGED_IN_EMAIL = "loggedInEmail"
    }

    fun setLoggedIn(isLoggedIn:Boolean){
        preferences.edit().putBoolean(IS_LOGGED_IN,isLoggedIn).apply()
    }
    fun isLoggedIn():Boolean{
        return preferences.getBoolean(IS_LOGGED_IN,false)
    }

    fun setLoggedInEmail(email: String) {
        preferences.edit().putString(LOGGED_IN_EMAIL, email).apply()
    }

    // Get logged-in email
    fun getLoggedInEmail(): String? {
        return preferences.getString(LOGGED_IN_EMAIL, null)
    }

    // Clear preferences (use this for logout)
    fun clearPreferences() {
        preferences.edit().clear().apply()
    }
}