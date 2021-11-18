package com.semicolon.yasunnae.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.DialogAskBinding

class AskDialog(
    private val context: Context,
    private val message: String,
    private val onYesClick: () -> Unit = {},
    private val onNoClick: () -> Unit = {}
) {

    fun callDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding: DialogAskBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_ask,
            null,
            false
        )
        binding.tvAsk.text = message
        binding.btnYesAsk.setOnClickListener {
            onYesClick()
            dialog.dismiss()
        }
        binding.btnNoAsk.setOnClickListener {
            onNoClick()
            dialog.dismiss()
        }
        dialog.show()
    }
}