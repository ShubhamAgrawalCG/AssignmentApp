package com.example.assignmentapp.utils

import com.example.assignmentapp.fragments.LoginFragment
import org.junit.Assert.*

import org.junit.Before

class LoginFragmentTest {

    lateinit var loginFragment: LoginFragment

    @Before
    fun setUp() {
        loginFragment = LoginFragment()
    }

    @org.junit.Test
    fun validUserName_inputEmptyString_expectedString(){
        val result = loginFragment.isEmailEmptyorNot("")
        assertEquals(false, result)
    }

    @org.junit.Test
    fun uiTest(){

    }

    fun validUserName_inputString_expectedString(){
        val result = loginFragment.isEmailEmptyorNot("shubham@gmail.co")
        assertEquals(true, result)
    }
}