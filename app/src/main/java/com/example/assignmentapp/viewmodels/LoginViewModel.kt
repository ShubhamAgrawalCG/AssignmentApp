package com.example.assignmentapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentapp.dataclasses.FirebaseRegistrationResponse
import com.example.assignmentapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var signInMutableLiveData = MutableLiveData<FirebaseRegistrationResponse>()

    val signInLiveData : LiveData<FirebaseRegistrationResponse> get() = signInMutableLiveData



    fun signInUserWithFirebase(email: String, password: String) = viewModelScope.launch(Dispatchers.IO){
        var result = repository.signInUserWithFirebase(email, password)
        signInMutableLiveData.postValue(FirebaseRegistrationResponse(result.isSuccessful, result.exception?.message.toString()))

    }
}