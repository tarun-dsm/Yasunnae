package com.semicolon.yasunnae.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.DialogNeedToVerifyLocationBinding

class NeedToVerifyLocationDialog(
    private val context: Context,
    private val onGoVerifyClick: () -> Unit
) {

    fun callDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding: DialogNeedToVerifyLocationBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_need_to_verify_location,
            null,
            false
        )
        dialog.setContentView(binding.root)
        binding.btnGoVerify.setOnClickListener {
            onGoVerifyClick()
            dialog.dismiss()
        }
        binding.btnDoItLater.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}