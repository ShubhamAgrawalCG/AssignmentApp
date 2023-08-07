package com.example.assignmentapp.utils

import android.app.DatePickerDialog
import android.content.Context
import android.util.Patterns
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.assignmentapp.R
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class FormValidation(val context: Context) {

     var gender = ""

     fun showDatePickerDialog(datePickerEditText: EditText, datePickerLayout: TextInputLayout) {
        val calendar: Calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val datePickerDialog = DatePickerDialog(
            context, { _, year, monthOfYear, dayOfMonth ->
                datePickerEditText.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                datePickerLayout.helperText = null
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }


     fun selectGender(radioGroup: RadioGroup) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            gender = if (checkedId == R.id.radio_male) "male" else "female"
        }
    }

    fun userNameFocusListener(usernameEditText: EditText, usernameLayout: TextInputLayout) {
        usernameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                usernameLayout.helperText = validUserName(usernameEditText.text.toString())
            }
        }
    }

    fun validUserName(userNameText: String): String? {
        if (!userNameText.matches("^[a-zA-Z ]+$".toRegex())) {
            return context.getString(R.string.only_characters_allow)
        }
        return null
    }

    fun emailFocusListener(emailEditText: EditText, emailLayout: TextInputLayout) {
        emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                emailLayout.helperText = validEmail(emailEditText.text.toString())
            }
        }
    }

    fun validEmail(emailText: String): String? {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return context.getString(R.string.invalid_email)
        }
        return null
    }

     fun passwordFocusListener(passwordEditText: EditText, passwordLayout: TextInputLayout) {
        passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                passwordLayout.helperText = validPassword(passwordEditText.text.toString())
            }
        }
    }

    fun validPassword(passwordText: String): String? {
        if (passwordText.length < 8) {
            return context.getString(R.string.minimum_eight_character_password)
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return context.getString(R.string.must_contain_one_lower_case)
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return context.getString(R.string.must_contain_one_upper_case)
        }
        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return context.getString(R.string.must_contain_one_special_case)
        }
        return null
    }

    fun submitForm(emailLayout: TextInputLayout, usernameLayout: TextInputLayout,
    passwordLayout: TextInputLayout, datePickerLayout: TextInputLayout, emailEditText: EditText,
    usernameEditText: EditText, passwordEditText: EditText): Pair<Boolean, String> {
        val result: Pair<Boolean, String>
        emailLayout.helperText = validEmail(emailEditText.text.toString())
        usernameLayout.helperText = validUserName(usernameEditText.text.toString())
        passwordLayout.helperText = validPassword(passwordEditText.text.toString())

        val validEmail = emailLayout.helperText == null
        val validUserName = usernameLayout.helperText == null
        val validPassword = passwordLayout.helperText == null
        val validDOB = datePickerLayout.helperText == null

        if (validEmail && validUserName && validPassword && !gender.isNullOrEmpty() && validDOB) {
            result = Pair(true, "Success")
        } else {
            invalidForm(emailLayout, usernameLayout, passwordLayout, datePickerLayout)
            result = Pair(false, "Error")
        }
        return result
    }

    // Show, if any error come
    private fun invalidForm(emailLayout: TextInputLayout, usernameLayout: TextInputLayout,
                            passwordLayout: TextInputLayout, datePickerLayout: TextInputLayout) {
        var message = StringBuilder()
        if (emailLayout.helperText != null)
            message =
                message.append(context.getString(R.string.email) + emailLayout.helperText)
        if (usernameLayout.helperText != null)
            message =
                message.append(context.getString(R.string.user_name) + usernameLayout.helperText)
        if (passwordLayout.helperText != null)
            message =
                message.append(context.getString(R.string.password) + passwordLayout.helperText)
        if (datePickerLayout.helperText != null) {
            message = message.append(context.getString(R.string.dob))
        }
        if (gender.isEmpty()) {
            message = message.append(context.getString(R.string.select_gender))
        }
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.invalid_form))
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.okay)) { _, _ ->
                // do nothing
            }
            .show()
    }

    // Form Successfully Submitted and Reset all User detail fields
    fun resetForm(emailLayout: TextInputLayout, usernameLayout: TextInputLayout,
                  passwordLayout: TextInputLayout, datePickerLayout: TextInputLayout,
                  emailEditText: EditText, usernameEditText: EditText, passwordEditText: EditText,
    datePickerEditText: EditText, radioGroup: RadioGroup) {
        emailEditText.text = null
        usernameEditText.text = null
        passwordEditText.text = null
        datePickerEditText.text = null
        radioGroup.clearCheck()

        emailLayout.helperText = context.getString(R.string.required)
        usernameLayout.helperText = context.getString(R.string.required)
        passwordLayout.helperText = context.getString(R.string.required)
        datePickerLayout.helperText = context.getString(R.string.required)
    }

    private fun clickOnLoginText(loginText: TextView) {
        loginText.setOnClickListener {

        }
    }
}