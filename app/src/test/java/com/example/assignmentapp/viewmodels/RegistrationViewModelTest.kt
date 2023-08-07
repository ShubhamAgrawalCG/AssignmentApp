package com.example.assignmentapp.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignmentapp.firebase.Firebase
import com.example.assignmentapp.repository.Repository
import com.example.assignmentapp.utils.getOrAwaitValue
import junit.framework.TestCase
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationViewModelTest : TestCase(){

    private lateinit var viewModel: RegistrationViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        super.setUp()
        val firebase = Firebase()
        val repository = Repository(firebase)
        viewModel = RegistrationViewModel(repository)
    }

    @Test
    fun testUserRegistration_validCredential(){
        viewModel.registerUserOnFirebase("abc@gmail.com", "Shubham@123")
        val result = viewModel.registrationResponseLiveData.getOrAwaitValue().isSuccess
        assertEquals(true, result)
    }

    @Test
    fun testUserRegistration_invalidCredential(){
        viewModel.registerUserOnFirebase("abc@g.ab", "Shubham@123")
        val result = viewModel.registrationResponseLiveData.getOrAwaitValue().isSuccess
        assertEquals(false, result)
    }

    @Test
    fun testSaveUserData_validCredential(){
        viewModel.saveUserDataOnFirebase("abc@gmail.com", "", "12/1/19992", "Male")
        val result = viewModel.userDetailsLiveData.getOrAwaitValue().isSuccess
        assertEquals(true, result)
    }

    @After
    public override fun tearDown() {
    }
}