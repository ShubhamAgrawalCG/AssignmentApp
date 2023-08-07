package com.example.assignmentapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceClass private constructor(){

    companion object {

        private const val IS_LOGIN = "is_Login"
        private val sharedPref = SharedPreferenceClass()
        private const val sharedPrefFile = "sharedPreference"
        private lateinit var sharedPreferences: SharedPreferences

        fun getInstance(context: Context): SharedPreferenceClass {
            if(!::sharedPreferences.isInitialized) {
                synchronized(SharedPreferenceClass::class.java) {
                    if (!::sharedPreferences.isInitialized) {
                        sharedPreferences =
                            context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                    }
                }
            }
            return sharedPref
        }
    }


    fun saveDataInSharedPreference(isLogin : Boolean){
        sharedPreferences.edit().putBoolean(IS_LOGIN, isLogin).apply()
    }

    val getUserId: Boolean
       get() = sharedPreferences.getBoolean(IS_LOGIN, false)
}