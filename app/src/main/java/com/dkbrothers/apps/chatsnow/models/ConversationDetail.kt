package com.dkbrothers.apps.chatsnow.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class ConversationDetail(
    val addresseeId:String = "",
    val messagesId:String = "",
    val writeTrigger:String = ""
)