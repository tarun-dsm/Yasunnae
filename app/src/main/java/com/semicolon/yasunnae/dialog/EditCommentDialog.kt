package com.semicolon.yasunnae.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.DialogEditCommentBinding

class EditCommentDialog(
    private val context: Context,
    private val presentComment: String,
    private val onEditClick: (comment: String) -> Unit
) {

    fun callDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding: DialogEditCommentBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_edit_comment,
            null,
            false
        )
        binding.presentComment = presentComment
        binding.btnEditComment.setOnClickListener {
            if (binding.etEditComment.text.toString().isEmpty()) return@setOnClickListener
            onEditClick(binding.etEditComment.text.toString())
        }
        binding.btnCancelEditComment.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}