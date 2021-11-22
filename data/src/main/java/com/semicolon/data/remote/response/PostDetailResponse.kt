package com.semicolon.data.remote.response

import com.google.gson.annotations.SerializedName
import com.semicolon.domain.entity.PostDetailEntity
import com.semicolon.domain.enum.toAnimalType

data class PostDetailResponse(

    @SerializedName("writer_id")
    val writerId: Int,

    @SerializedName("nickname")
    val nickname: String,

    @SerializedName("rating")
    val rating: String,

    @SerializedName("is_mine")
    val isMine: Boolean,

    @SerializedName("is_applied")
    val isApplied: Boolean,

    @SerializedName("post")
    val post: PostDetail,

    @SerializedName("pet")
    val pet: PetDetail
)

data class PostDetail(

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

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("is_application_end")
    val isApplicationEnd: Boolean,

    @SerializedName("is_updated")
    val isUpdated: Boolean
)

data class PetDetail(

    @SerializedName("pet_name")
    val petName: String,

    @SerializedName("pet_species")
    val petSpecies: String,

    @SerializedName("pet_sex")
    val petSex: String,

    @SerializedName("file_paths")
    val filePaths: List<String>,

    @SerializedName("animal_type")
    val animalType: String
)

fun PostDetailResponse.toEntity() = PostDetailEntity(
    writerId = this.writerId,
    nickname = this.nickname,
    rating = this.rating,
    isMine = this.isMine,
    isApplied = this.isApplied,
    post = PostDetailEntity.PostInfo(
        title = this.post.title,
        protectionStartDate = this.post.protectionStartDate,
        protectionEndDate = this.post.protectionEndDate,
        applicationEndDate = this.post.applicationEndDate,
        description = this.post.description,
        contactInfo = this.post.contactInfo,
        createdAt = this.post.createdAt,
        isApplicationEnd = this.post.isApplicationEnd,
        isUpdated = this.post.isUpdated
    ),
    pet = PostDetailEntity.PetInfo(
        petName = this.pet.petName,
        petSpecies = this.pet.petSpecies,
        petSex = this.pet.petSex,
        filePaths = this.pet.filePaths,
        animalType = this.pet.animalType.toAnimalType()
    )
)