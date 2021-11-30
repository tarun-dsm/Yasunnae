package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.entity.InterestedEntity

data class HasInterestedResponse(

    @SerializedName("is_true")
    val isTrue: Boolean
)

fun HasInterestedResponse.toEntity() =
    InterestedEntity(isTrue)