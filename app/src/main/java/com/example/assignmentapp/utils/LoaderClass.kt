package com.example.assignmentapp.utils

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet


class LoaderClass(val context: Context) {


    private var progressBar = ProgressBar(context)

    fun initLoader(constraintLayout: ConstraintLayout){
        progressBar.id = View.generateViewId()
        progressBar.translationZ = 10f
        constraintLayout.addView(progressBar)
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(progressBar.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT)
        constraintSet.connect(progressBar.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT)
        constraintSet.connect(progressBar.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
        constraintSet.connect(progressBar.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
        constraintSet.applyTo(constraintLayout)
    }

    fun showLoader(){
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoader(){
        progressBar.visibility = View.GONE
    }
}
