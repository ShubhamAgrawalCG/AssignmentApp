package com.example.assignmentapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignmentapp.databinding.ListItemLayoutBinding
import com.example.assignmentapp.dataclasses.UserData

class HomeAdapter(private val arrayList: ArrayList<UserData>) : RecyclerView.Adapter<HomeAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.binding.user = arrayList[position]
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class CustomViewHolder(val binding: ListItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}