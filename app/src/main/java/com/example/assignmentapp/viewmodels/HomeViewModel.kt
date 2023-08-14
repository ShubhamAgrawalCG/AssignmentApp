package com.example.assignmentapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentapp.dataclasses.UserData
import com.example.assignmentapp.dataclasses.UserDataListResponse
import com.example.assignmentapp.repository.Repository
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
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
        val resultData = repository.getUserDataFromFirebase()
        saveUserResponseInDataClass(resultData)
    }

    fun saveUserResponseInDataClass(resultData: Task<QuerySnapshot>){
        var arrayList = arrayListOf <UserData>()
        if (resultData.isSuccessful){
            for (userValues in resultData.getResult()) {
                arrayList.add(UserData(userValues.get("email").toString(), userValues.get("name").toString(),
                    userValues.get("dob").toString(), userValues.get("gender").toString()))
            }
            userListLiveData.postValue(UserDataListResponse(true,arrayList,null))
        } else{
            userListLiveData.postValue(UserDataListResponse(false, arrayList, resultData.exception?.message.toString()))
        }
    }

    fun signOutUser() {
        val result = repository.signOutCurrentUser()
        signOutMutuableLiveData.postValue(result)
    }
}