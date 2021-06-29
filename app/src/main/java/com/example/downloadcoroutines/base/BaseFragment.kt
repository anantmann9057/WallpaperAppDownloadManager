package com.nexogic.base

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.utils.Preferences
import kotlinx.android.synthetic.main.layout_dialog.*

open class BaseFragment : Fragment() {
    lateinit var preference: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = Preferences(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun showDialog() {
        (context as BaseActivity).dialog.show()
    }

    fun dismissDialog() {
        (context as BaseActivity).dialog.dismiss()
    }


}