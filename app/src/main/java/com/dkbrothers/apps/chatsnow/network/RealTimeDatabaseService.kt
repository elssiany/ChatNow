package com.dkbrothers.apps.chatsnow.network

import com.crashlytics.android.Crashlytics
import com.dkbrothers.apps.chatsnow.models.Conversation
import com.dkbrothers.apps.chatsnow.models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RealTimeDatabaseService {

    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun getConversations(callback: Callback<ArrayList<Conversation>>){
        firebaseDatabase.getReference("conversations")
            .child(FirebaseAuth.getInstance().currentUser?.uid?:"")
            .addValueEventListener(object : ValueEventListener {
                val list = arrayListOf<Conversation>()
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (conversationSnapshot in dataSnapshot.children) {
                    dataSnapshot.getValue(Conversation::class.java)?.let {
                        list.add(it)
                    }
                }
                callback.onSuccess(list)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Crashlytics.logException(databaseError.toException())
                callback.onFailed(databaseError.code)
            }
        })
    }

    fun getMessages(messageId:String, callback: Callback<ArrayList<Message>>){
        firebaseDatabase.getReference("messages").child(messageId)
            .addValueEventListener(object : ValueEventListener {
                val list = arrayListOf<Message>()
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (conversationSnapshot in dataSnapshot.children) {
                        dataSnapshot.getValue(Message::class.java)?.let {
                            list.add(it)
                        }
                    }
                    callback.onSuccess(list)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Crashlytics.logException(databaseError.toException())
                    callback.onFailed(databaseError.code)
                }
            })
    }

}