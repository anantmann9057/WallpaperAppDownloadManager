package com.example.downloadcoroutines.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.WEB_LINK
import com.nexogic.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {
    val url by lazy { intent.getStringExtra(WEB_LINK) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        setWebView()

    }

    fun setWebView() {
        wvProfileLinks.loadUrl(url!!)
        wvProfileLinks.settings.javaScriptCanOpenWindowsAutomatically = false
        wvProfileLinks.settings.javaScriptEnabled = true
        wvProfileLinks.settings.loadsImagesAutomatically = true
        wvProfileLinks.settings.allowFileAccess = true
        wvProfileLinks.webViewClient = client
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
}