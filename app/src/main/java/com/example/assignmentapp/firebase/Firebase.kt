package com.example.assignmentapp.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.assignmentapp.dataclasses.FirebaseRegistrationResponse
import com.example.assignmentapp.dataclasses.UserData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.*
import javax.inject.Inject

class Firebase @Inject constructor() {

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val firebaseDatabase by lazy { FirebaseFirestore.getInstance() }

    //Register User On Firebase with Username and Password

    suspend fun registerUserToFirebase(
        userName: String,
        password: String
    ): Task<AuthResult> {

        val deferred = CompletableDeferred<Task<AuthResult>>()
        firebaseAuth.createUserWithEmailAndPassword(userName, password)
            .addOnCompleteListener(OnCompleteListener {
                deferred.complete(it)
                Log.d("demo", "success" + it.isSuccessful + "exception: " + it.exception)
            })
        return deferred.await()

    }

    // Save User detail in Firebase FireStore Database
     suspend fun saveDataToFiresStore(userName: String, name: String, dob: String, gender: String) : Task<DocumentReference> {
        val deferred = CompletableDeferred<Task<DocumentReference>>()
        val userData = UserData(userName, name, dob, gender)
        firebaseDatabase.collection("users")
            .add(userData).addOnCompleteListener {
                deferred.complete(it)
            }
        return deferred.await()
    }

    //SignIn user with email and password
    suspend fun signInUserWithFirebase(email: String, password: String): Task<AuthResult> {
        val deferred = CompletableDeferred<Task<AuthResult>>()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                deferred.complete(it)
            }
        return deferred.await()
    }

    //Fetch User Data From Firebase Firestore
    suspend fun getUserListFromFirebase() : Task<QuerySnapshot> {
        val deferred = CompletableDeferred<Task<QuerySnapshot>>()
        firebaseDatabase.collection("users").get()
            .addOnCompleteListener {
                deferred.complete(it)
            }
        return deferred.await()
    }

    // Signout User from Firebase
     fun signOutFromFirebase() : Boolean {
        val deferred = CompletableDeferred<Boolean>()
        firebaseAuth.signOut()
        return deferred.complete(true)
    }
}