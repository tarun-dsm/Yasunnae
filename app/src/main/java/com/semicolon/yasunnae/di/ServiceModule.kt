package com.semicolon.yasunnae.di

import com.semicolon.domain.base.ErrorHandler
import com.semicolon.domain.repository.*
import com.semicolon.domain.service.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideAccountService(
        accountRepository: AccountRepository,
        errorHandler: ErrorHandler
    ): AccountService =
        AccountServiceImpl(accountRepository, errorHandler)

    @Provides
    fun provideApplicationService(
        applicationRepository: ApplicationRepository,
        errorHandler: ErrorHandler
    ): ApplicationService =
        ApplicationServiceImpl(applicationRepository, errorHandler)

    @Provides
    fun provideAuthService(
        authRepository: AuthRepository,
        errorHandler: ErrorHandler
    ): AuthService =
        AuthServiceImpl(authRepository, errorHandler)

    @Provides
    fun provideEmailService(
        emailRepository: EmailRepository,
        errorHandler: ErrorHandler
    ): EmailService =
        EmailServiceImpl(emailRepository, errorHandler)

    @Provides
    fun providePostService(
        postRepository: PostRepository,
        errorHandler: ErrorHandler
    ): PostService =
        PostServiceImpl(postRepository, errorHandler)

    @Provides
    fun provideProfileService(
        profileRepository: ProfileRepository,
        errorHandler: ErrorHandler
    ): ProfileService =
        ProfileServiceImpl(profileRepository, errorHandler)

    @Provides
    fun provideReviewService(
        reviewRepository: ReviewRepository,
        errorHandler: ErrorHandler
    ): ReviewService =
        ReviewServiceImpl(reviewRepository, errorHandler)

    @Provides
    fun provideCommentService(
        commentRepository: CommentRepository,
        errorHandler: ErrorHandler
    ): CommentService =
        CommentServiceImpl(commentRepository, errorHandler)
}