package com.nexogic.base


import android.app.Dialog
import android.os.Bundle
import android.view.Window
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import com.example.downloadcoroutines.R

import com.example.downloadcoroutines.utils.Preferences
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_dialog.*

open class BaseActivity : FragmentActivity() {
    lateinit var preference: Preferences
    private var mSnackBar: Snackbar? = null
    val dialog by lazy { Dialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = Preferences(this)
        setDialog()


    }

    private fun setDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_dialog)

        dialog.let {
            it.animeLoading.setAnimationFromUrl("https://assets4.lottiefiles.com/packages/lf20_kJNwM4.json")
        }
    }


    fun showDialog() {
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }


}