package com.example.assignmentapp.utils

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackBarClass {

    companion object {

        fun showSnackBar(context: Context, view: View, errorMsg: String) {
            Snackbar.make(context, view, errorMsg, Snackbar.LENGTH_LONG).show()
        }
    }
}