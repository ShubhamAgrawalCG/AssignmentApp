package com.example.assignmentapp.repository

import com.example.assignmentapp.firebase.Firebase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class Repository @Inject constructor(private val firebase: Firebase) {

    suspend fun registerUserOnFirebase(email: String, password: String): Task<AuthResult> {
        return firebase.registerUserToFirebase(email, password)
    }

    suspend fun saveDataOnFirebase(email: String, name: String, dob: String, gender: String) : Task<DocumentReference> {
        return firebase.saveDataToFiresStore(email, name, dob, gender)
    }

    suspend fun signInUserWithFirebase(email: String, password: String): Task<AuthResult>{
        return firebase.signInUserWithFirebase(email, password)
    }

    suspend fun getUserDataFromFirebase(): Task<QuerySnapshot> {
        return firebase.getUserListFromFirebase()
    }

    fun signOutCurrentUser() : Boolean {
        return firebase.signOutFromFirebase()
    }
}