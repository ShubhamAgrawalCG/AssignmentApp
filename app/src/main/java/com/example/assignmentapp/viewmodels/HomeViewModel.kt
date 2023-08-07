package com.example.assignmentapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentapp.dataclasses.UserData
import com.example.assignmentapp.dataclasses.UserDataListResponse
import com.example.assignmentapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var userListLiveData = MutableLiveData<UserDataListResponse>()
    var signOutMutuableLiveData = MutableLiveData<Boolean>()
    val firebaseUserListLiveData: LiveData<UserDataListResponse> get() = userListLiveData
    val firebaseUserSignOutLiveData: LiveData<Boolean> get() = signOutMutuableLiveData

    fun getUserListFromFirebase() = viewModelScope.launch(Dispatchers.IO) {
        var resultData = repository.getUserDataFromFirebase()
        if (resultData.isSuccessful){
            for (userValues in resultData.result) {
                userListLiveData.postValue(
                    UserDataListResponse(true,
                        UserData(userValues.data.get("email") as String, userValues.data.get("name") as String,
                        userValues.data.get("dob") as String, userValues.data.get("gender") as String),
                        null
                    )
                )
            }
        } else{
            userListLiveData.postValue(UserDataListResponse(false, null, resultData.exception?.message.toString()))
        }


    }

    fun signOutUser() {
        val result = repository.signOutCurrentUser()
        signOutMutuableLiveData.postValue(result)
    }
}