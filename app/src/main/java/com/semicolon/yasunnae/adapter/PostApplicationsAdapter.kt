package com.semicolon.yasunnae.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.semicolon.domain.entity.PostApplicationEntity
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.ItemPostApplicationBinding

class PostApplicationsAdapter(
    private val context: Context,
    private val isDecided: Boolean,
    private val acceptedApplicationId: Int,
    private val onItemClick: (applicantId: Int) -> Unit,
    private val onAcceptClick: (applicationId: Int) -> Unit,
    private val onWriteReviewClick: (applicationId: Int) -> Unit
) : RecyclerView.Adapter<PostApplicationsAdapter.ViewHolder>() {

    var postApplications = ArrayList<PostApplicationEntity>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostApplicationsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_post_application, parent, false))
    }

    override fun onBindViewHolder(holder: PostApplicationsAdapter.ViewHolder, position: Int) {
        holder.itemPostApplication.context = context
        holder.itemPostApplication.application = postApplications[position]
        if (!isDecided) holder.itemPostApplication.btnAcceptApplication.isEnabled = true
        if (postApplications[position].applicationId == acceptedApplicationId)
            holder.itemPostApplication.btnWriteReviewPostApplication.visibility = VISIBLE
    }

    override fun getItemCount(): Int =
        postApplications.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemPostApplication: ItemPostApplicationBinding = DataBindingUtil.bind(itemView)!!

        init {
            itemView.setOnClickListener {
                onItemClick(postApplications[adapterPosition].applicantId)
            }
            itemPostApplication.btnAcceptApplication.setOnClickListener {
                onAcceptClick(postApplications[adapterPosition].applicationId)
            }
            itemPostApplication.btnWriteReviewPostApplication.setOnClickListener {
                onWriteReviewClick(postApplications[adapterPosition].applicationId)
            }
        }
    }
}