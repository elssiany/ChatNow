package com.dkbrothers.apps.chatsnow.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dkbrothers.apps.chatsnow.R
import com.dkbrothers.apps.chatsnow.models.Conversation
import com.dkbrothers.apps.chatsnow.network.Callback
import com.dkbrothers.apps.chatsnow.network.RealTimeDatabaseService

class HomeActivityViewModel: ViewModel() {

    lateinit var listener:Listener
    val realTimeDatabaseService = RealTimeDatabaseService()
    val listConversations: MutableLiveData<ArrayList<Conversation>> = MutableLiveData()
    val isLoading = MutableLiveData(false)

    fun getConversationFromFirebase(){
        realTimeDatabaseService.getConversations(object: Callback<ArrayList<Conversation>> {
            override fun onSuccess(result: ArrayList<Conversation>?) {
                listConversations.postValue(result)
                processFinish()
            }

            override fun onFailed(errorCode: Int) {
                processFinish()
                when(errorCode){
                    1->{
                        listener.onError(R.string.message_error_1)
                    }
                    2->{
                        listener.onError(R.string.message_error_2)
                    }
                }
            }

        })
    }

    fun processFinish(){
        isLoading.value = true
    }

    interface Listener{
        fun onError(stringId: Int)
    }

}