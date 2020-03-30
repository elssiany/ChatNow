package com.dkbrothers.apps.chatsnow.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dkbrothers.apps.chatsnow.R
import com.dkbrothers.apps.chatsnow.databinding.ItemConversationBinding
import com.dkbrothers.apps.chatsnow.models.Conversation


class ConversationsAdapter(private val activity: Activity) :
        RecyclerView.Adapter<ConversationsAdapter.HolderAppsAdapter>() {

    private var conversations: ArrayList<Conversation> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): HolderAppsAdapter {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_conversation,
            viewGroup, false)
        return HolderAppsAdapter(view)
    }

    override fun getItemCount(): Int = conversations.size

    override fun onBindViewHolder(holder: HolderAppsAdapter, position: Int) {

        val conversation = conversations[position]

        holder.setDataCard(conversation)

        holder.binding.root.setOnClickListener { onClick(conversation) }

        Glide.with(activity)
            .load(conversation.urlPhoto)
            .into(holder.binding.img)

    }

    private fun onClick(conversation: Conversation) {

    }

    fun AddConversations(conversations: ArrayList<Conversation>){
        this.conversations = conversations
        notifyDataSetChanged()
    }

    fun AddConversation(conversation: Conversation){
        this.conversations.add(conversations.size,conversation)
        notifyItemInserted(conversations.size-1)
    }

    class HolderAppsAdapter(view: View) : RecyclerView.ViewHolder(view) {
        var binding: ItemConversationBinding = ItemConversationBinding.bind(view)
        fun setDataCard(data: Conversation){
            binding.setVariable(2, data)
            binding.notifyChange()
        }
    }

}