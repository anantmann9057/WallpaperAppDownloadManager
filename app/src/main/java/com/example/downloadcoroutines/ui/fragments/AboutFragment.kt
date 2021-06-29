package com.example.downloadcoroutines.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
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
        animeHeaderInfo.let {
            it.setAnimationFromUrl("https://assets10.lottiefiles.com/packages/lf20_totrpclr.json")
        }
        animeLinkedIn.let {
            it.setAnimationFromUrl("https://assets3.lottiefiles.com/private_files/lf30_nfrwwnhp.json")
            it.setOnClickListener {
                intent.putExtra(WEB_LINK, "https://www.linkedin.com/in/anantmann/")
                startActivity(intent)
            }
        }

        animeFacebook.let {
            it.setAnimationFromUrl("https://assets9.lottiefiles.com/packages/lf20_oGizJg.json")
            it.setOnClickListener {
                intent.putExtra(WEB_LINK, "https://www.facebook.com/anant.mann.5")
                startActivity(intent)
            }
        }
        animeGithub.let {
            it.setAnimationFromUrl("https://assets2.lottiefiles.com/packages/lf20_dgBN4P.json")
            it.setOnClickListener {
                intent.putExtra(WEB_LINK, "https://github.com/anantmann9057")
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

}