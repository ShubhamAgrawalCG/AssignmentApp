package com.example.assignmentapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.assignmentapp.R
import com.example.assignmentapp.viewmodels.RegistrationViewModel
import com.example.assignmentapp.databinding.FragmentRegistrationBinding
import com.example.assignmentapp.utils.FormValidation
import com.example.assignmentapp.utils.LoaderClass
import com.example.assignmentapp.utils.SnackBarClass
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val registrationViewModel by viewModels<RegistrationViewModel>()
    private lateinit var loader: LoaderClass
    private lateinit var formValidation: FormValidation
    private lateinit var userNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var datePickerEditText: EditText
    private lateinit var usernameLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var datePickerLayout: TextInputLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initViewListeners()
        clickOnRegistrationButton()
        clickOnLoginButton()
        observeFirebaseResponse()
    }

    private fun initViews(){
        formValidation = FormValidation(requireContext())
        emailEditText = binding.emailEditText
        userNameEditText = binding.usernameEditText
        passwordEditText = binding.passwordEditText
        datePickerEditText = binding.datePickerEditText
        emailLayout = binding.emailLayout
        usernameLayout = binding.usernameLayout
        passwordLayout = binding.passwordLayout
        datePickerLayout = binding.datePickerLayout
    }

    private fun initViewListeners(){
        formValidation.userNameFocusListener(userNameEditText, usernameLayout)
        formValidation.emailFocusListener(emailEditText, emailLayout)
        formValidation.passwordFocusListener(passwordEditText, passwordLayout)
        datePickerEditText.setOnClickListener {
            formValidation.showDatePickerDialog(datePickerEditText, datePickerLayout)
        }
        formValidation.selectGender(binding.radioGroup)
    }

     fun registerUser() {
         val email = emailEditText.text.toString()
         val password = passwordEditText.text.toString()
        registrationViewModel.registerUserOnFirebase(email, password)
    }

    fun saveUserData(){
        val emailText = binding.emailEditText.text.toString()
        val nameText = binding.usernameEditText.text.toString()
        val dobText = binding.datePickerEditText.text.toString()
        var genderText = formValidation.gender
        registrationViewModel.saveUserDataOnFirebase(emailText, nameText, dobText, genderText)
    }

    private fun observeFirebaseResponse() {
        registrationViewModel.registrationResponseLiveData.observe(this, {
            if (it.isSuccess) {
                saveUserData()
            } else {
                SnackBarClass.showSnackBar(requireContext(), requireView(), it.error)
                loader.hideLoader()
            }
        })

        registrationViewModel.userDetailsLiveData.observe(this,{
            if (it.isSuccess){
                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
            }
            else{
                showSnackBar(it.error)
            }
            loader.hideLoader()
        })
    }

    private fun clickOnRegistrationButton(){
        binding.registerButton.setOnClickListener {
            val validateInput = submitRegistrationForm()
            if (validateInput.first) {
                showLoader()
                registerUser()
            }
        }
    }

    private fun submitRegistrationForm() : Pair<Boolean, String>{
        return formValidation.submitForm(emailLayout, usernameLayout, passwordLayout,
        datePickerLayout, emailEditText, userNameEditText, passwordEditText)
    }

    private fun resetRegistrationForm(){
        formValidation.resetForm(emailLayout, usernameLayout, passwordLayout,
            datePickerLayout, emailEditText, userNameEditText, passwordEditText,
        datePickerEditText, binding.radioGroup)
    }

    private fun showSnackBar(errorMsg : String){
        SnackBarClass.showSnackBar(requireContext(), requireView(), errorMsg)
    }

    private fun showLoader(){
        loader = LoaderClass(requireContext())
        loader.initLoader(binding.mainConstraint)
        loader.showLoader()
    }

    private fun clickOnLoginButton(){
        binding.loginText.setOnClickListener {
            moveToRegistrationPage()
        }
    }

    private fun moveToRegistrationPage(){
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.navigate(R.id.loginFragment)
    }
}