package com.nexogic.base


import android.os.Bundle
import androidx.fragment.app.FragmentActivity

import com.example.downloadcoroutines.utils.Preferences
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : FragmentActivity() {
    lateinit var preference: Preferences
    private var mSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = Preferences(this)


    }




}