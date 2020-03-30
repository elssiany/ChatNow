package com.dkbrothers.apps.chatsnow.view.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import com.dkbrothers.apps.chatsnow.R
import com.google.firebase.analytics.FirebaseAnalytics


abstract class BaseActivity : AppCompatActivity() {

    private var contentLoadingProgressBar: ContentLoadingProgressBar? = null

    private val mFirebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    /**
     * Metodo usado para inicializar el <contentLoadingProgressBar> en un Activity
     * */
    fun initContentLoadingProgressBar() {
        contentLoadingProgressBar = findViewById(R.id.contentLoadingProgressBar)
    }

    /**
     * Metodo usado para inicializar el <contentLoadingProgressBar> en un Fragment
     * */
    fun initContentLoadingProgressBar(contentLoadingProgressBar: ContentLoadingProgressBar?) {
        this.contentLoadingProgressBar = contentLoadingProgressBar
    }

    fun showContentLoadingProgressBar() {
        contentLoadingProgressBar?.visibility = View.VISIBLE
        contentLoadingProgressBar?.show()
    }

    fun hideContentLoadingProgressBar() {
        contentLoadingProgressBar?.visibility = View.GONE
        contentLoadingProgressBar?.hide()
    }

    fun marcarEvento(evento: String) {
        mFirebaseAnalytics.logEvent(evento, null)
    }

    fun marcarEvento(evento: String, args: Bundle) {
        mFirebaseAnalytics.logEvent(evento, args)
    }

    fun marcarPantalla(pantalla: String) {
        mFirebaseAnalytics.setCurrentScreen(this, pantalla, null)
    }


}
