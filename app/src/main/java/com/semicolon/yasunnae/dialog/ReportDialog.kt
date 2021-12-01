package com.semicolon.yasunnae.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.DialogReportBinding

class ReportDialog(
    private val context: Context,
    private val onReportClick: (reason: String) -> Unit
) {

    fun callDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding: DialogReportBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_report,
            null,
            false
        )
        dialog.setContentView(binding.root)

        binding.btnDoReport.setOnClickListener {
            if (binding.etReport.text.toString().isEmpty()) return@setOnClickListener
            onReportClick(binding.etReport.text.toString())
            dialog.dismiss()
        }
        binding.btnCancelReport.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}