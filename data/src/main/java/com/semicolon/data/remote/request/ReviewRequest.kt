package com.semicolon.data.remote.request

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.param.ReviewParam

data class ReviewRequest(

    @SerializedName("grade")
    val grade: Double,

    @SerializedName("comment")
    val comment: String
)

fun ReviewParam.toRequestParam() = ReviewRequest(
    grade = this.grade,
    comment = this.comment
)