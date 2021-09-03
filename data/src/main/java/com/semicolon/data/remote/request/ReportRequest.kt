package com.semicolon.data.remote.request

import com.google.gson.annotations.SerializedName

data class ReportRequest(

    @SerializedName("comment")
    val comment: String
)