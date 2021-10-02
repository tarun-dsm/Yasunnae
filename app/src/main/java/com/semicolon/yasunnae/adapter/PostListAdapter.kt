package com.semicolon.yasunnae.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.semicolon.domain.entity.PostEntity
import com.semicolon.domain.enum.AnimalType
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.ItemPostBinding

class PostListAdapter(
    private val onItemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(postId: Int)
    }

    private var postList = ArrayList<PostEntity>()
    private var totalPostList = ArrayList<PostEntity>()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PostListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_post, parent, false))
    }

    override fun onBindViewHolder(holder: PostListAdapter.ViewHolder, position: Int) {
        holder.itemPostBinding.post = postList[position]
    }

    override fun getItemCount(): Int =
        postList.size

    fun setList(postList: List<PostEntity>) {
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
        postList = ArrayList(totalPostList.filter { it.animalType == category })
        notifyDataSetChanged()
        return postList.isNotEmpty()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemPostBinding: ItemPostBinding = DataBindingUtil.bind(itemView)!!

        init {
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(postList[adapterPosition].id)
            }
        }
    }
}