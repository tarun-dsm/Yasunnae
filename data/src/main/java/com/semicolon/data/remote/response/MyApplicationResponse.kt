package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.entity.ApplicationEntity

data class MyApplicationResponse(

    @SerializedName("my_applications")
    val myApplication: List<Application>
)

data class Application(

    @SerializedName("id")
    val id: Int,

    @SerializedName("post_name")
    val postName: String,

    @SerializedName("post_id")
    val postId: Int,

    @SerializedName("is_accepted")
    val isAccepted: Boolean,

    @SerializedName("is_end")
    val isEnd: Boolean,

    @SerializedName("first_image_path")
    val firstImagePath: String,

    @SerializedName("start_date")
    val startDate: String,

    @SerializedName("end_date")
    val endDate: String,

    @SerializedName("administration_division")
    val administrationDivision: String,

    @SerializedName("is_written_review")
    val isWrittenReview: Boolean
)

fun Application.toEntity() = ApplicationEntity(
    id = this.id,
    postName = this.postName,
    postId = this.postId,
    isAccepted = this.isAccepted,
    isEnd = this.isEnd,
    firstImagePath = firstImagePath,
    protectionStartDate = startDate,
    protectionEndDate = endDate,
    administrationDivision = administrationDivision,
    isWrittenReview = isWrittenReview
)
