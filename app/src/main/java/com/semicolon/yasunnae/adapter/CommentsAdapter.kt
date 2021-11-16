package com.semicolon.yasunnae.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.semicolon.domain.entity.CommentEntity
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.ItemCommentBinding

class CommentsAdapter(
    private val onEditClick: (comment: CommentEntity) -> Unit,
    private val onDeleteClick: (comment: CommentEntity) -> Unit
) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    private var commentList = ArrayList<CommentEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_comment, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemCommentBinding.comment = commentList[position]
    }

    override fun getItemCount(): Int =
        commentList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setCommentList(commentList: List<CommentEntity>) {
        this.commentList = ArrayList(commentList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemCommentBinding: ItemCommentBinding = DataBindingUtil.bind(itemView)!!

        init {
            itemCommentBinding.btnEditComment.setOnClickListener {
                onEditClick(commentList[adapterPosition])
            }
            itemCommentBinding.btnDeleteComment.setOnClickListener {
                onDeleteClick(commentList[adapterPosition])
            }
        }
    }
}