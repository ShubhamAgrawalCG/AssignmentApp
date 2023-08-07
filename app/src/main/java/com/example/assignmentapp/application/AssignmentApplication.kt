package com.example.assignmentapp.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AssignmentApplication : Application() {

   // val registrationRepository by lazy { RegistrationRepository(database.getUserLogInDao()) }
}