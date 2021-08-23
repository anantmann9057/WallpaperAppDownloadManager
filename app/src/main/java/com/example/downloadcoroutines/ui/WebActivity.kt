package com.example.downloadcoroutines.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.WEB_LINK
import com.example.downloadcoroutines.databinding.ActivityWebBinding
import com.nexogic.base.BaseActivity

class WebActivity : BaseActivity() {
    lateinit var url: String
    lateinit var binding: ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web)
        url = intent.getStringExtra(WEB_LINK)!!
        setWebView()

    }

    fun setWebView() {
        binding.wvProfileLinks.let {
            it.loadUrl(url)
            it.settings.javaScriptCanOpenWindowsAutomatically = false
            it.settings.javaScriptEnabled = true
            it.settings.loadsImagesAutomatically = true
            it.settings.allowFileAccess = true
            it.webViewClient = client
        }

    }

    var client = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            showDialog()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            dismissDialog()
        }
    }

    override fun onBackPressed() {
        if (binding.wvProfileLinks.canGoBack()) {
            binding.wvProfileLinks.goBack()
        } else
            super.onBackPressed()
    }
}