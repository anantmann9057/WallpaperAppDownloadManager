package com.example.downloadcoroutines.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.downloadcoroutines.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    lateinit var animeList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        animeList = ArrayList()
        animeList.add("https://assets3.lottiefiles.com/packages/lf20_ihjvzraq.json")
        animeList.add("https://assets4.lottiefiles.com/packages/lf20_e0JHCg.json")
        animeList.add("https://assets4.lottiefiles.com/packages/lf20_jkvgdceo.json")
        animeList.add("https://assets4.lottiefiles.com/packages/lf20_goeb1fbr.json")
        animeList.add("https://assets4.lottiefiles.com/packages/lf20_jcikwtux.json")
        animeList.shuffle()
        animeSplash.setAnimationFromUrl(animeList[0])
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        }
    }
}