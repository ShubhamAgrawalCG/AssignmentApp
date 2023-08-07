package com.example.assignmentapp.utils

import com.example.assignmentapp.fragmnets.LoginFragment
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule

class LoginFragmentTest {

    lateinit var loginFragment: LoginFragment

    @Before
    fun setUp() {
        loginFragment = LoginFragment()
    }

    @org.junit.Test
    fun validUserName_inputEmptyString_expectedString(){
        var result = loginFragment.isEmailEmptyorNot("")
        assertEquals(false, result)
    }

    @org.junit.Test
    fun uiTest(){

    }

    fun validUserName_inputString_expectedString(){
        var result = loginFragment.isEmailEmptyorNot("shubham@gmail.co")
        assertEquals(true, result)
    }
}