package com.example.assignmentapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefenceClass private constructor(){

    companion object {

        private val IS_LOGIN = "is_Login"
        private val sharedPref = SharedPrefenceClass()
        private val sharedPrefFile = "sharedPreference"
        private lateinit var sharedPreferences: SharedPreferences

        fun getInstance(context: Context): SharedPrefenceClass {
            if(!::sharedPreferences.isInitialized) {
                synchronized(SharedPrefenceClass::class.java) {
                    if (!::sharedPreferences.isInitialized) {
                        sharedPreferences =
                            context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
                    }
                }
            }
            return sharedPref
        }
    }


    fun saveData_InSharedPrefence(isLogin : Boolean){
        sharedPreferences.edit().putBoolean(IS_LOGIN, isLogin).apply()
    }

    val getUserId: Boolean
       get() = sharedPreferences.getBoolean(IS_LOGIN, false)
}