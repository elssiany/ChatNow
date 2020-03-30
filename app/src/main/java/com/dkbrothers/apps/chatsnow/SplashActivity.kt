package com.dkbrothers.apps.chatsnow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dkbrothers.apps.chatsnow.view.ui.HomeActivity
import com.dkbrothers.apps.chatsnow.view.ui.IntroActivity
import loadPreference

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_splash)
        delaySplashScreen()
    }

    fun delaySplashScreen() {
        val delaySplash: Long = 2500
        Handler().postDelayed({
            //Start Activity
            var intent = Intent(this, IntroActivity::class.java)
            if(loadPreference("showHome", false)) {
                intent = Intent(this, HomeActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, delaySplash)
    }

}
