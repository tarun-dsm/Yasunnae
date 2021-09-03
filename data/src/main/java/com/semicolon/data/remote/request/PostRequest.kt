package com.semicolon.data.remote.request

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.param.PostParam

data class PostRequest(

    @SerializedName("title")
    val title: String,

    @SerializedName("protection_start_date")
    val protectionStartDate: String,

    @SerializedName("protection_end_date")
    val protectionEndDate: String,

    @SerializedName("application_end_date")
    val applicationEndDate: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("contact_info")
    val contactInfo: String,

    @SerializedName("pet_name")
    val petName: String,

    @SerializedName("pet_species")
    val petSpecies: String,

    @SerializedName("pet_sex")
    val petSex: String
)

fun PostParam.toRequestParam() = PostRequest(
    title = this.title,
    protectionStartDate = this.protectionStartDate,
    protectionEndDate = this.protectionEndDate,
    applicationEndDate = this.applicationEndDate,
    description = this.description,
    contactInfo = this.contactInfo,
    petName = this.petName,
    petSpecies = this.petSpecies,
    petSex = this.petSex
)