package com.semicolon.yasunnae.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.semicolon.yasunnae.R
import com.semicolon.yasunnae.databinding.DialogSelectDateBinding
import com.semicolon.yasunnae.util.format
import java.util.*

class SelectDateDialog(
    private val context: Context,
    private val minDate: Date? = null,
    private val maxDate: Date? = null,
    private val onOkClick: (selectedDate: String) -> Unit
) {

    fun callDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val binding: DialogSelectDateBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_select_date,
            null,
            false
        )
        dialog.setContentView(binding.root)

        dialog.setContentView(binding.root)
        binding.cvSelectDate.minDate = Date().time
        if (minDate != null) binding.cvSelectDate.minDate = minDate.time
        if (maxDate != null) binding.cvSelectDate.maxDate = maxDate.time

        val dateFormat = "yyyy-MM-dd"
        var selectedDate: String = minDate?.format(dateFormat) ?: Date().format(dateFormat)
        binding.cvSelectDate.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedMonth =
                if ((month + 1).toString().length == 1) "0" + (month + 1).toString()
                else (month + 1).toString()
            val selectedDay =
                if (dayOfMonth.toString().length == 1) "0$dayOfMonth"
                else dayOfMonth.toString()
            selectedDate = "${year}-$selectedMonth-$selectedDay"
        }
        binding.btnOkSelectDate.setOnClickListener {
            onOkClick(selectedDate)
            dialog.dismiss()
        }
        dialog.show()
    }
}