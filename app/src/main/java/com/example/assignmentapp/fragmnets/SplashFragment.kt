package com.example.assignmentapp.fragmnets

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.assignmentapp.R
import com.example.assignmentapp.databinding.FragmentSplashBinding
import com.example.assignmentapp.utils.SharedPrefenceClass

class SplashFragment : Fragment() {

    private lateinit var binding : FragmentSplashBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLottieAnimation()
        moveToNextFragment()

    }

    private fun initLottieAnimation(){
        binding.apply {
            animationView.setAnimation("lottie_animation.json")
            animationView.loop(true)
            animationView.playAnimation()
        }
    }

    private fun moveToNextFragment(){
        Handler(Looper.getMainLooper()).postDelayed({
            if(SharedPrefenceClass.getInstance(requireContext()).getUserId){
                moveToHomeFragment()
            } else{
                moveToRegistrationFragment()
            }
           cancelLottieAnimation()
        }, 3000)
    }

    private fun cancelLottieAnimation(){
        if (binding.animationView.isAnimating){
            binding.animationView.cancelAnimation()
        }
    }

    private fun moveToRegistrationFragment(){
        findNavController().navigate(R.id.action_splashFragment_to_registrationFragment)
    }

    private fun moveToHomeFragment(){
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }
}