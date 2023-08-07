package com.example.assignmentapp.fragmnets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentapp.R
import com.example.assignmentapp.adapters.HomeAdapter
import com.example.assignmentapp.dataclasses.UserData
import com.example.assignmentapp.databinding.FragmentHomeBinding
import com.example.assignmentapp.utils.LoaderClass
import com.example.assignmentapp.utils.SnackBarClass
import com.example.assignmentapp.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private var arrayList: ArrayList<UserData> = arrayListOf()
    private lateinit var loaderClass: LoaderClass

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLoader()
        clickOnSignOutButton()
        getUsersFromFirebase()
        observeUserListLiveData()
    }

    private fun getUsersFromFirebase() {
        homeViewModel.getUserListFromFirebase()
    }

    private fun observeUserListLiveData() {
        homeViewModel.firebaseUserListLiveData.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                arrayList.add(it.userData!!)
                setAdapter()
                loaderClass.hideLoader()
            } else {
                SnackBarClass.showSnackBar(requireContext(), requireView(), it.error.toString())
            }
        }

        homeViewModel.firebaseUserSignOutLiveData.observe(viewLifecycleOwner){
            if (it) moveToLoginFragment()
        }
    }

    private fun setAdapter() {
        var recyclerView = binding.recyclerView
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HomeAdapter(arrayList)
    }

    private fun clickOnSignOutButton(){
        binding.signoutButton.setOnClickListener {
            homeViewModel.signOutUser()
        }
    }

    private fun initLoader(){
        loaderClass = LoaderClass(requireContext())
        loaderClass.initLoader(binding.mainConstraint)
        loaderClass.showLoader()
    }

    private fun moveToLoginFragment(){
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        navController.popBackStack(R.id.homeFragment, true)
        navController.navigate(R.id.loginFragment)
    }
}