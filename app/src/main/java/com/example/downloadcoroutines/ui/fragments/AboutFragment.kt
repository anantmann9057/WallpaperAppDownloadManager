package com.example.downloadcoroutines.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.utils.WEB_LINK
import com.example.downloadcoroutines.ui.WebActivity
import com.example.downloadcoroutines.base.BaseFragment
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

    private fun setAnimations() {
        val intent = Intent(requireContext(), WebActivity::class.java)
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
        val window: Window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.blue_50)
    }

}