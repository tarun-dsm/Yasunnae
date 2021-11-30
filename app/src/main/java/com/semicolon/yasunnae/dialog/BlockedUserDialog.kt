package com.semicolon.yasunnae.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.DialogBlockedUserBinding

class BlockedUserDialog(
    private val context: Context
) {

    fun callDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding: DialogBlockedUserBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_blocked_user,
            null,
            false
        )
        dialog.setContentView(binding.root)

        binding.btnBlockedOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}