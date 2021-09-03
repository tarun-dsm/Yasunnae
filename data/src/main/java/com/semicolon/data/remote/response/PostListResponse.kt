package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.entity.PostEntity

data class PostListResponse(

    @SerializedName("posts")
    val posts: List<Post>
)

data class Post(

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("first_image_path")
    val firstImagePath: String,

    @SerializedName("administration_division")
    val administrationDivision: String,

    @SerializedName("protection_start_date")
    val protectionStartDate: String,

    @SerializedName("protection_end_date")
    val protectionEndDate: String
)

fun Post.toEntity() = PostEntity(
    id = this.id,
    title = this.title,
    firstImagePath = this.firstImagePath,
    administrationDivision = this.administrationDivision,
    protectionStartDate = this.protectionStartDate,
    protectionEndDate = this.protectionEndDate
)