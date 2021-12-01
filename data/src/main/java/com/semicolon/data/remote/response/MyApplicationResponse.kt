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

    @SerializedName("first_image_path")
    val firstImagePath: String,

    @SerializedName("administration_division")
    val administrationDivision: String,

    @SerializedName("start_date")
    val protectionStartDate: String,

    @SerializedName("end_date")
    val protectionEndDate: String,

    @SerializedName("accepted")
    val isAccepted: Boolean,

    @SerializedName("end")
    val isEnd: Boolean,
)

fun Application.toEntity() = ApplicationEntity(
    id = this.id,
    postName = this.postName,
    postId = this.postId,
    firstImagePath = this.firstImagePath,
    administrationDivision = this.administrationDivision,
    protectionStartDate = this.protectionStartDate,
    protectionEndDate = this.protectionEndDate,
    isAccepted = this.isAccepted,
    isEnd = this.isEnd,
)
