package com.example.assignmentapp.viewmodels


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignmentapp.firebase.Firebase
import com.example.assignmentapp.repository.Repository
import com.example.assignmentapp.utils.getOrAwaitValue
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest : TestCase() {

    private lateinit var viewModel: HomeViewModel

    @get: Rule
     val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        super.setUp()
        val firebase = Firebase()
        val repository = Repository(firebase)
        viewModel = HomeViewModel(repository)
    }

    @org.junit.Test
    fun testUserLogin_validCredential(){
        viewModel.getUserListFromFirebase()
        val result = viewModel.userListLiveData.getOrAwaitValue().isSuccess
        assertEquals(true, result)
    }
}