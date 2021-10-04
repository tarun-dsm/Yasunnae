package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.entity.ProfilePostEntity

data class ProfilePostResponse(

    @SerializedName("posts")
    val posts: List<ProfilePost>
)

data class ProfilePost(

    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("first_image_path")
    val firstImagePath: String,

    @SerializedName("protector_id")
    val protectorId: String,

    @SerializedName("protector_nickname")
    val protectorNickname: String
)

fun ProfilePost.toEntity() = ProfilePostEntity(
    id = this.id,
    title = this.title,
    createdAt = this.createdAt,
    nickname = this.nickname,
    firstImagePath = this.firstImagePath,
    protectorId = this.protectorId,
    protectorNickname = this.protectorNickname
)