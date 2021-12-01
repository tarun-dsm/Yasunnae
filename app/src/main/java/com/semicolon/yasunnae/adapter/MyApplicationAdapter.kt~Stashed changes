package com.semicolon.yasunnae.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.semicolon.domain.entity.ApplicationEntity
import com.semicolon.domain.enum.AnimalType
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.ItemApplicationsPostBinding

class MyApplicationAdapter(private val onItemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<MyApplicationAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(postId: Int)
    }

    private var postList = ArrayList<ApplicationEntity>()
    private var totalPostList = ArrayList<ApplicationEntity>()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyApplicationAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_applications_post, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemPostBinding.application = postList[position]
    }

    override fun getItemCount(): Int =
        postList.size

    fun setList(postList: List<ApplicationEntity>) {
        this.totalPostList = ArrayList(postList)
        resetCategory()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetCategory(): Boolean {
        postList = totalPostList
        this.notifyDataSetChanged()
        return postList.isNotEmpty()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCategory(category: AnimalType): Boolean {
        notifyDataSetChanged()
        return postList.isNotEmpty()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemPostBinding: ItemApplicationsPostBinding = DataBindingUtil.bind(itemView)!!

        init {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(postList[adapterPosition].id)
            }
        }
    }

}