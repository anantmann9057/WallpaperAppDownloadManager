package com.example.downloadcoroutines.ui

import android.app.PictureInPictureParams
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Rational
import android.view.Display
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
        setSplashAnime()

        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        }
    }

    fun setSplashAnime() {
        animeList = ArrayList()
        animeList.apply {
            add("https://assets3.lottiefiles.com/packages/lf20_ihjvzraq.json")
            add("https://assets4.lottiefiles.com/packages/lf20_e0JHCg.json")
            add("https://assets4.lottiefiles.com/packages/lf20_jkvgdceo.json")
            add("https://assets4.lottiefiles.com/packages/lf20_goeb1fbr.json")
            add("https://assets4.lottiefiles.com/packages/lf20_jcikwtux.json")
        }
        animeList.shuffle()
        animeSplash.setAnimationFromUrl(animeList[0])
    }


}