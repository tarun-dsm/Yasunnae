package com.semicolon.yasunnae.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.semicolon.domain.entity.ReviewEntity
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.ItemReviewBinding

class ReviewsAdapter(
    val onEditClick: (review: ReviewEntity) -> Unit,
    val onDeleteClick: (review: ReviewEntity) -> Unit
) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    private var reviews = ArrayList<ReviewEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_review, parent, false))
    }

    override fun onBindViewHolder(holder: ReviewsAdapter.ViewHolder, position: Int) {
        holder.binding.review = reviews[position]
    }

    override fun getItemCount(): Int =
        reviews.size

    @SuppressLint("NotifyDataSetChanged")
    fun setReviews(reviews: List<ReviewEntity>) {
        this.reviews = ArrayList(reviews)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemReviewBinding = DataBindingUtil.bind(itemView)!!

        init {
            binding.btnEditReview.setOnClickListener { onEditClick(reviews[adapterPosition]) }
            binding.btnDeleteReview.setOnClickListener { onDeleteClick(reviews[adapterPosition]) }
        }
    }
}