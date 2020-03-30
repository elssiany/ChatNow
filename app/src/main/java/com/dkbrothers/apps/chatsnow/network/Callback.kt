package com.dkbrothers.apps.chatsnow.network

interface Callback<T> {

    fun onSuccess(result:T?)

    fun onFailed(errorCode:Int)

}