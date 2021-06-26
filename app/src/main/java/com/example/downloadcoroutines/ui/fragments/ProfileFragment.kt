package com.example.downloadcoroutines.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.animeProfile.setAnimationFromUrl("https://assets5.lottiefiles.com/packages/lf20_ibsbm9w2.json")
        binding.animeProfilePic.setAnimationFromUrl("https://assets7.lottiefiles.com/datafiles/6deVuMSwjYosId3/data.json")
        return binding.root
    }

}