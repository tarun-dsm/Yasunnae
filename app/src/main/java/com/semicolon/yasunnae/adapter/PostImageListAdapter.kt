package com.semicolon.yasunnae.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.ItemPostImageBinding
import com.semicolon.yasunnae.util.convertUrlToFile
import java.io.File

class PostImageListAdapter(
    val context: Context,
    val onImageAdded: () -> Unit
) : RecyclerView.Adapter<PostImageListAdapter.ViewHolder>() {

    private var postImageList = ArrayList<File>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_post_image, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemPostImageBinding.imageFile = postImageList[position]
    }

    override fun getItemCount(): Int {
        return postImageList.size
    }

    fun getPostImageList() = postImageList

    fun setPostImageList(urlList: List<String>) {
        urlList.map {
            convertUrlToFile(context, it)
                .subscribe { file ->
                    addPostImageList(file)
                    onImageAdded()
                }
        }
    }

    fun addPostImageList(file: File) {
        postImageList.add(file)
        notifyItemInserted(itemCount - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearPostImage() {
        postImageList.clear()
        notifyDataSetChanged()
    }

    fun deletePostImageList(position: Int) {
        postImageList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemPostImageBinding: ItemPostImageBinding = DataBindingUtil.bind(itemView)!!
    }
}