package com.semicolon.yasunnae.di

import com.semicolon.data.datasource.*
import com.semicolon.data.repository.*
import com.semicolon.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAccountRepository(
        accountDataSource: AccountDataSource
    ): AccountRepository =
        AccountRepositoryImpl(accountDataSource)

    @Provides
    fun provideApplicationRepository(
        applicationDataSource: ApplicationDataSource
    ): ApplicationRepository =
        ApplicationRepositoryImpl(applicationDataSource)

    @Provides
    fun provideAuthRepository(
        authDataSource: AuthDataSource
    ): AuthRepository =
        AuthRepositoryImpl(authDataSource)

    @Provides
    fun provideEmailRepository(
        emailDataSource: EmailDataSource
    ): EmailRepository =
        EmailRepositoryImpl(emailDataSource)

    @Provides
    fun providePostRepository(
        postDataSource: PostDataSource
    ): PostRepository =
        PostRepositoryImpl(postDataSource)

    @Provides
    fun provideProfileRepository(
        profileDataSource: ProfileDataSource
    ): ProfileRepository =
        ProfileRepositoryImpl(profileDataSource)

    @Provides
    fun provideReviewRepository(
        reviewDataSource: ReviewDataSource
    ): ReviewRepository =
        ReviewRepositoryImpl(reviewDataSource)

    @Provides
    fun provideCommentRepository(
        commentDataSource: CommentDataSource
    ): CommentRepository =
        CommentRepositoryImpl(commentDataSource)
}