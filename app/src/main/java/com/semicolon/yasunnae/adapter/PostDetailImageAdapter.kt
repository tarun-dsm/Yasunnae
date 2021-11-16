package com.semicolon.yasunnae.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.ItemPostDetailImageBinding

class PostDetailImageAdapter : RecyclerView.Adapter<PostDetailImageAdapter.ViewHolder>() {

    private var imageUrlList = ArrayList<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostDetailImageAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_post_detail_image, parent, false))
    }

    override fun onBindViewHolder(holder: PostDetailImageAdapter.ViewHolder, position: Int) {
        holder.itemPostDetailImageBinding.imageUrl = imageUrlList[position]
    }

    override fun getItemCount(): Int =
        imageUrlList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setCommentList(imageUrlList: List<String>) {
        this.imageUrlList = ArrayList(imageUrlList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemPostDetailImageBinding: ItemPostDetailImageBinding =
            DataBindingUtil.bind(itemView)!!
    }
}