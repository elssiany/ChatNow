package com.dkbrothers.apps.chatsnow.view.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dkbrothers.apps.chatsnow.R
import com.dkbrothers.apps.chatsnow.view.adapters.ConversationsAdapter
import com.dkbrothers.apps.chatsnow.viewmodels.HomeActivityViewModel

class HomeActivity : BaseActivity(), HomeActivityViewModel.Listener {

    private lateinit var viewModel:HomeActivityViewModel
    private lateinit var conversationAdapter: ConversationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModel = ViewModelProvider(this).get(HomeActivityViewModel::class.java)
        viewModel.listener = this
        initViews()
        observeViewModel()
        viewModel.getConversationFromFirebase()
    }


    private fun initViews(){
        initContentLoadingProgressBar()
        conversationAdapter = ConversationsAdapter(this)
        val rvConversations =  findViewById<RecyclerView>(R.id.rv_conversations)
        rvConversations.layoutManager = LinearLayoutManager(this)
        rvConversations.adapter = conversationAdapter
    }

    private fun observeViewModel(){
        viewModel.listConversations.observe(this, Observer {
            conversationAdapter.AddConversations(it)
        })
        viewModel.isLoading.observe(this, Observer {
            if(it)
                hideContentLoadingProgressBar()
        })
    }


    override fun onError(stringId: Int) {
        Toast.makeText(this, getString(stringId), Toast.LENGTH_LONG).show()
    }

    fun onClick(view: View) {
        Toast.makeText(this, getString(R.string.no_disponible_en_estos_momentos),
            Toast.LENGTH_LONG).show()
    }


}
