package com.example.downloadcoroutines.base


import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.utils.Preferences
import kotlinx.android.synthetic.main.layout_dialog.*
import javax.inject.Inject

open class BaseActivity : FragmentActivity() {
    @Inject
    lateinit var preference: Preferences

    lateinit var animeList: ArrayList<String>

    val dialog by lazy { Dialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDialog()


    }

     fun setDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog)
        animeList = ArrayList()
        animeList.add("https://assets4.lottiefiles.com/packages/lf20_kJNwM4.json")
        animeList.add("https://assets8.lottiefiles.com/packages/lf20_x62chJ.json")
        animeList.add("https://assets8.lottiefiles.com/packages/lf20_Z4BhGL.json")
        animeList.add("https://assets8.lottiefiles.com/packages/lf20_jny63J.json")
        animeList.add("https://assets8.lottiefiles.com/packages/lf20_rPGSco.json")
        animeList.add("https://assets8.lottiefiles.com/packages/lf20_YMim6w.json")

        dialog.let {
            it.animeLoading.setAnimationFromUrl(animeList[0])
        }
    }


    fun showDialog() {
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }


}