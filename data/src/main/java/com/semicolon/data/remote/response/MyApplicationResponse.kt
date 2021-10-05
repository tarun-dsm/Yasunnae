package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.entity.ApplicationEntity

data class MyApplicationResponse(

    @SerializedName("my_application")
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

    @SerializedName("application_date")
    val applicationDate: String
)

fun Application.toEntity() = ApplicationEntity(
    id = this.id,
    postName = this.postName,
    postId = this.postId,
    isAccepted = this.isAccepted,
    isEnd = this.isEnd,
    applicationDate = this.applicationDate
)
