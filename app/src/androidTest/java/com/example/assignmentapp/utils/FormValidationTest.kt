package com.example.assignmentapp.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.assignmentapp.R
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class FormValidationTest {

    private lateinit var formValidation: FormValidation
    private lateinit var context: Context

    @Before
    fun setUp(){
        context = ApplicationProvider.getApplicationContext()
        formValidation = FormValidation(context)
    }

    @Test
    fun validUserName_inputEmptyString_expectedString() {
        val result = formValidation.validUserName("")
        assertEquals(context.getString(R.string.only_characters_allow), result)
    }

    @Test
    fun validUserName_inputString_expectedNull() {
        val result = formValidation.validUserName("John")
        assertEquals(null, result)
    }

    @Test
    fun validUserName_inputNumber_expectedString() {
        val result = formValidation.validUserName("123")
        assertEquals(context.getString(R.string.only_characters_allow), result)
    }

    @Test
    fun validEmail_inputEmptyString_expectedString() {
        val result = formValidation.validEmail("")
        assertEquals(context.getString(R.string.invalid_email), result)

    }

    @Test
    fun validEmail_inputInvalidString_expectedString() {
        val result = formValidation.validEmail("john@co")
        assertEquals(context.getString(R.string.invalid_email), result)
    }

    @Test
    fun validEmail_inputvalidString_expectedNull() {
        val result = formValidation.validEmail("john@gmail.com")
        assertEquals(null, result)
    }

    @Test
    fun validPassword_inputEmptyString_expectedString() {
        val result = formValidation.validPassword("")
        assertEquals(context.getString(R.string.minimum_eight_character_password), result)
    }

    @Test
    fun validPassword_inputStringWithoutLowerCase_expectedString() {
        val result = formValidation.validPassword("JOHN@1231")
        assertEquals(context.getString(R.string.must_contain_one_lower_case), result)
    }

    @Test
    fun validPassword_inputStringWithoutUpperCase_expectedString() {
        val result = formValidation.validPassword("john@asdf")
        assertEquals(context.getString(R.string.must_contain_one_upper_case), result)
    }

    @Test
    fun validPassword_inputStringWithoutSpecialCase_expectedString() {
        val result = formValidation.validPassword("Johnmartin")
        assertEquals(context.getString(R.string.must_contain_one_special_case), result)
    }

    @Test
    fun validPassword_inputString_expectedNull() {
        val result = formValidation.validPassword("John@maruti")
        assertEquals(null, result)
    }

}