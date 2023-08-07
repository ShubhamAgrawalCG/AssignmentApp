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
class RegistrationViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    var firebaseRegistrationResponseMutableLiveData =
        MutableLiveData<FirebaseRegistrationResponse>()
    private var userDetailsMutuableLiveData = MutableLiveData<FirebaseRegistrationResponse>()

    val registrationResponseLiveData: LiveData<FirebaseRegistrationResponse> get() = firebaseRegistrationResponseMutableLiveData
    val userDetailsLiveData: LiveData<FirebaseRegistrationResponse> get() = userDetailsMutuableLiveData

    fun registerUserOnFirebase(email: String, password: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val responseData = repository.registerUserOnFirebase(email, password)
            firebaseRegistrationResponseMutableLiveData.postValue(
                FirebaseRegistrationResponse(
                    responseData.isSuccessful,
                    responseData.exception?.message.toString()
                )
            )
        }

    fun saveUserDataOnFirebase(email: String, name: String, dob: String, gender: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val responseData = repository.saveDataOnFirebase(email, name, dob, gender)
            userDetailsMutuableLiveData.postValue(
                FirebaseRegistrationResponse(
                    responseData.isSuccessful,
                    responseData.exception?.message.toString()
                )
            )
        }
}