package com.example.downloadcoroutines.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.utils.WEB_LINK
import com.example.downloadcoroutines.databinding.ActivityWebBinding
import com.example.downloadcoroutines.base.BaseActivity

class WebActivity : BaseActivity() {
    lateinit var url: String
    lateinit var binding: ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web)
        setViews()
    }

   private fun setViews() {
        setWebView()
   }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        url = intent.getStringExtra(WEB_LINK)!!

        binding.wvProfileLinks.let {
            it.loadUrl(url)
            it.settings.javaScriptCanOpenWindowsAutomatically = false
            it.settings.javaScriptEnabled = true
            it.settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK)
            it.settings.setAppCacheEnabled(true)
            it.settings.loadsImagesAutomatically = true
            it.settings.allowFileAccess = true
            it.webViewClient = client
        }

    }

   private var client = object : WebViewClient() {
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