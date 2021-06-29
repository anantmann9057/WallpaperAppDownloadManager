package com.example.downloadcoroutines.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.downloadcoroutines.R
import com.nexogic.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        animeHeaderInfo.setAnimationFromUrl("https://assets10.lottiefiles.com/packages/lf20_totrpclr.json")
        animeLinkedIn.setAnimationFromUrl("https://assets3.lottiefiles.com/private_files/lf30_nfrwwnhp.json")
        animeFacebook.setAnimationFromUrl("https://assets9.lottiefiles.com/packages/lf20_oGizJg.json")
        animeInstagram.setAnimationFromUrl("https://assets4.lottiefiles.com/packages/lf20_q5efz2fv.json")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)

    }

    override fun onStart() {
        super.onStart()
    }

}