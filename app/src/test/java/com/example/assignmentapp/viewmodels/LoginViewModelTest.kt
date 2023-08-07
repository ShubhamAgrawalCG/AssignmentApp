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
class LoginViewModelTest : TestCase() {

    private lateinit var viewModel: LoginViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        super.setUp()
        val firebase = Firebase()
        val repository = Repository(firebase)
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun testUserLogin_validCredential(){
        viewModel.signInUserWithFirebase("akash@gmail.com", "Akash@123")
        val result = viewModel.signInLiveData.getOrAwaitValue().isSuccess
        assertEquals(true, result)
    }

    @Test
    fun testUserLogin_invalidCredential(){
        viewModel.signInUserWithFirebase("akash@gmail.com", "Akash@12345678")
        val result = viewModel.signInLiveData.getOrAwaitValue().error
        assertEquals("The password is invalid or the user does not have a password.", result)
    }

    @After
    public override fun tearDown() {
    }
}