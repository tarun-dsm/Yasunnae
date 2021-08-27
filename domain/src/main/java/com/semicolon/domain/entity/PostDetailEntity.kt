package com.semicolon.domain.entity

data class PostDetailEntity(

    val nickname: String,

    val rating: String,

    val post: PostInfo,

    val pet: PetInfo
){
    data class PostInfo(

        val title: String,

        val protectionStartDate: String,

        val protectionEndDate: String,

        val applicationEndDate: String,

        val description: String,

        val contactInfo: String,

        val createdAt: String,

        val isApplicationEnd: Boolean,

        val isUpdated: Boolean
    )

    data class PetInfo(

        val petName: String,

        val petSpecies: String,

        val petSex: String,

        val filePaths: List<String>
    )
}
