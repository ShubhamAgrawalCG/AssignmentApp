package com.example.assignmentapp.fragments

import org.junit.Assert.*
import org.junit.Before

class LoginFragmentTest {

    private lateinit var loginFragment: LoginFragment

    @Before
    fun setUp() {
        loginFragment = LoginFragment()
    }

    @org.junit.Test
    fun validUserName_inputEmptyString_expectedString(){
        val result = loginFragment.isEmailEmptyOrNot("")
        assertEquals(false, result)
    }

    @org.junit.Test
    fun validUserName_inputString_expectedString(){
        val result = loginFragment.isEmailEmptyOrNot("shubham@gmail.co")
        assertEquals(true, result)
    }
}