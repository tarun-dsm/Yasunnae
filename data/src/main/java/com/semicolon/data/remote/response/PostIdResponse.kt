package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName

data class PostIdResponse(

    @SerializedName("post_id")
    val postId: Int
)