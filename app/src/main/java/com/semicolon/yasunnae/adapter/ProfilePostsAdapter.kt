package com.semicolon.yasunnae.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.semicolon.domain.entity.ProfilePostEntity
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.ItemProfilePostBinding

class ProfilePostsAdapter(
    val onPostClick: (profilePost: ProfilePostEntity) -> Unit
) : RecyclerView.Adapter<ProfilePostsAdapter.ViewHolder>() {

    private var profilePosts = ArrayList<ProfilePostEntity>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfilePostsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_profile_post, parent, false))
    }

    override fun onBindViewHolder(holder: ProfilePostsAdapter.ViewHolder, position: Int) {
        holder.binding.profilePost = profilePosts[position]
    }

    override fun getItemCount(): Int =
        profilePosts.size

    @SuppressLint("NotifyDataSetChanged")
    fun setProfilePosts(profilePosts: List<ProfilePostEntity>) {
        this.profilePosts = ArrayList(profilePosts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemProfilePostBinding = DataBindingUtil.bind(itemView)!!

        init {
            itemView.setOnClickListener { onPostClick(profilePosts[adapterPosition]) }
        }
    }
}