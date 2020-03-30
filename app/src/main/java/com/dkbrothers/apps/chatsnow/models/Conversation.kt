package com.dkbrothers.apps.chatsnow.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Conversation(
    val title:String = "",
    val urlPhoto:String = "",
    val isNewMessage:Boolean = false,
    @Exclude
    val date:String = "",
    val lastTimestampMessage:Long = 0L,
    val lastShortMessage:String = ""
)