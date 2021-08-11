package com.example.downloadcoroutines.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.WEB_LINK
import com.example.downloadcoroutines.ui.WebActivity
import com.nexogic.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setAnimations()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)

    }

    fun setAnimations() {
        var intent = Intent(requireContext(), WebActivity::class.java)
        animeHeaderInfo.apply {
            setAnimationFromUrl("https://assets10.lottiefiles.com/packages/lf20_totrpclr.json")
        }
        animeLinkedIn.apply {
            setAnimationFromUrl("https://assets7.lottiefiles.com/packages/lf20_hwvh0smy.json")
            speed = 0.5f
            setOnClickListener {
                intent.putExtra(WEB_LINK, "https://www.linkedin.com/in/anantmann/")
                startActivity(intent)
            }
        }

        animeFacebook.apply {
            setAnimationFromUrl("https://assets9.lottiefiles.com/packages/lf20_oGizJg.json")
            setOnClickListener {
                intent.putExtra(WEB_LINK, "https://www.facebook.com/anant.mann.5")
                startActivity(intent)
            }
        }
        animeGithub.apply {
            setAnimationFromUrl("https://assets2.lottiefiles.com/packages/lf20_dgBN4P.json")
            setOnClickListener {
                intent.putExtra(WEB_LINK, "https://github.com/anantmann9057")
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

}