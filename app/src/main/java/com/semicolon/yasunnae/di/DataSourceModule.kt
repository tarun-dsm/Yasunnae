package com.semicolon.yasunnae.di

import com.semicolon.data.datasource.*
import com.semicolon.data.local.TokenStorage
import com.semicolon.data.remote.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideAccountDataSource(
        accountApi: AccountApi,
        tokenStorage: TokenStorage
    ): AccountDataSource =
        AccountDataSourceImpl(tokenStorage, accountApi)

    @Provides
    fun provideApplicationDataSource(
        applicationApi: ApplicationApi,
        tokenStorage: TokenStorage
    ): ApplicationDataSource =
        ApplicationDataSourceImpl(tokenStorage, applicationApi)

    @Provides
    fun provideAuthDataSource(
        authApi: AuthApi,
        tokenStorage: TokenStorage
    ): AuthDataSource =
        AuthDataSourceImpl(tokenStorage, authApi)

    @Provides
    fun provideEmailDataSource(
        emailApi: EmailApi
    ): EmailDataSource =
        EmailDataSourceImpl(emailApi)

    @Provides
    fun providePostDataSource(
        postApi: PostApi,
        tokenStorage: TokenStorage
    ): PostDataSource =
        PostDataSourceImpl(tokenStorage, postApi)

    @Provides
    fun provideProfileDataSource(
        profileApi: ProfileApi,
        tokenStorage: TokenStorage
    ): ProfileDataSource =
        ProfileDataSourceImpl(tokenStorage, profileApi)

    @Provides
    fun provideReviewDataSource(
        reviewApi: ReviewApi,
        tokenStorage: TokenStorage
    ): ReviewDataSource =
        ReviewDataSourceImpl(tokenStorage, reviewApi)

    @Provides
    fun provideCommentDataSource(
        commentApi: CommentApi,
        tokenStorage: TokenStorage
    ): CommentDataSource =
        CommentDataSourceImpl(tokenStorage, commentApi)
}