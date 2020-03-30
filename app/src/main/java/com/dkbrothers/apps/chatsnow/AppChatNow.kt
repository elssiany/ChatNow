package com.dkbrothers.apps.chatsnow

import android.app.Application
import com.google.firebase.database.FirebaseDatabase


class AppChatNow:Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}