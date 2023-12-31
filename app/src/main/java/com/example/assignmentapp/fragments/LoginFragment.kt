package com.example.assignmentapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.assignmentapp.R
import com.example.assignmentapp.databinding.FragmentLoginBinding
import com.example.assignmentapp.utils.SharedPreferenceClass
import com.example.assignmentapp.utils.SnackBarClass
import com.example.assignmentapp.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickOnLoginButton()
        clickOnRegistrationButton()
        observeViewModelData()
    }

    // Observe response from view model of live data
    private fun observeViewModelData() {
        loginViewModel.signInLiveData.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                SharedPreferenceClass.getInstance(requireContext()).saveDataInSharedPreference(true)
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                SnackBarClass.showSnackBar(requireContext(), requireView(), it.error)
            }
        }
    }

    private fun clickOnLoginButton(){
        binding.loginButton.setOnClickListener {

            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (isEmailEmptyOrNot(email) && isPasswordEmptyOrNot(password)){
                loginViewModel.signInUserWithFirebase(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString())
            } else{
                SnackBarClass.showSnackBar(requireContext(), requireView(), "UserName or Password is empty")
            }
        }
    }

     fun isEmailEmptyOrNot(email: String) : Boolean{
       return email.isNotEmpty()
    }

    private fun isPasswordEmptyOrNot(password: String) : Boolean{
        return password.isNotEmpty()
    }

    private fun clickOnRegistrationButton(){
        binding.registerText.setOnClickListener {
            moveToRegistrationPage()
        }
    }

    private fun moveToRegistrationPage(){
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.popBackStack(R.id.loginFragment, true)
        navController.navigate(R.id.registrationFragment)
    }
}