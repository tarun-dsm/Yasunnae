package com.semicolon.yasunnae.di

import com.semicolon.domain.service.*
import com.semicolon.domain.usecase.account.*
import com.semicolon.domain.usecase.application.*
import com.semicolon.domain.usecase.auth.*
import com.semicolon.domain.usecase.comment.AddCommentUseCase
import com.semicolon.domain.usecase.comment.DeleteCommentUseCase
import com.semicolon.domain.usecase.comment.FixCommentUseCase
import com.semicolon.domain.usecase.comment.GetCommentListUseCase
import com.semicolon.domain.usecase.email.*
import com.semicolon.domain.usecase.post.*
import com.semicolon.domain.usecase.profile.*
import com.semicolon.domain.usecase.review.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideCheckEmailDuplicationUseCase(
        accountService: AccountService
    ) = CheckEmailDuplicationUseCase(accountService)

    @Provides
    fun provideCheckNicknameDuplicationUseCase(
        accountService: AccountService
    ) = CheckNicknameDuplicationUseCase(accountService)

    @Provides
    fun provideRegisterAccountUseCase(
        accountService: AccountService
    ) = RegisterAccountUseCase(accountService)

    @Provides
    fun provideReportUserUseCase(
        accountService: AccountService
    ) = ReportUserUseCase(accountService)

    @Provides
    fun provideSaveCoordinateUseCase(
        accountService: AccountService
    ) = SaveCoordinateUseCase(accountService)

    @Provides
    fun provideAcceptApplicationUseCase(
        applicationService: ApplicationService
    ) = AcceptApplicationUseCase(applicationService)

    @Provides
    fun provideCancelApplicationUseCase(
        applicationService: ApplicationService
    ) = CancelApplicationUseCase(applicationService)

    @Provides
    fun provideGetMyApplicationListUseCase(
        applicationService: ApplicationService
    ) = GetMyApplicationListUseCase(applicationService)

    @Provides
    fun provideSendApplicationUseCase(
        applicationService: ApplicationService
    ) = SendApplicationUseCase(applicationService)

    @Provides
    fun provideLoginUseCase(
        authService: AuthService
    ) = LoginUseCase(authService)

    @Provides
    fun provideTokenRefreshUseCase(
        authService: AuthService
    ) = TokenRefreshUseCase(authService)

    @Provides
    fun provideLogoutUseCase(
        authService: AuthService
    ) = LogoutUseCase(authService)

    @Provides
    fun provideCheckCertificationNumberUseCase(
        emailService: EmailService
    ) = CheckCertificationNumberUseCase(emailService)

    @Provides
    fun provideSendCertificationMailUseCase(
        emailService: EmailService
    ) = SendCertificationMailUseCase(emailService)

    @Provides
    fun provideDeletePostUseCase(
        postService: PostService
    ) = DeletePostUseCase(postService)

    @Provides
    fun provideFixPostUseCase(
        postService: PostService
    ) = FixPostUseCase(postService)

    @Provides
    fun provideGetPostApplicationUseCase(
        postService: PostService
    ) = GetPostApplicationUseCase(postService)

    @Provides
    fun provideGetPostDetailUseCase(
        postService: PostService
    ) = GetPostDetailUseCase(postService)

    @Provides
    fun provideGetPostListUseCase(
        postService: PostService
    ) = GetPostListUseCase(postService)

    @Provides
    fun provideSendPostImageUseCase(
        postService: PostService
    ) = SendPostImageUseCase(postService)

    @Provides
    fun provideWritePostUseCase(
        postService: PostService
    ) = WritePostUseCase(postService)

    @Provides
    fun provideGetProfilePostUseCase(
        profileService: ProfileService
    ) = GetProfilePostUseCase(profileService)

    @Provides
    fun provideGetProfileUseCase(
        profileService: ProfileService
    ) = GetProfileUseCase(profileService)

    @Provides
    fun provideGetReviewUseCase(
        profileService: ProfileService
    ) = GetReviewUseCase(profileService)

    @Provides
    fun provideDeleteReviewUseCase(
        reviewService: ReviewService
    ) = DeleteReviewUseCase(reviewService)

    @Provides
    fun provideEditReviewUseCase(
        reviewService: ReviewService
    ) = EditReviewUseCase(reviewService)

    @Provides
    fun provideWriteReviewUseCase(
        reviewService: ReviewService
    ) = WriteReviewUseCase(reviewService)

    @Provides
    fun provideAddCommentUseCase(
        commentService: CommentService
    ) = AddCommentUseCase(commentService)

    @Provides
    fun provideFixCommentUseCase(
        commentService: CommentService
    ) = FixCommentUseCase(commentService)

    @Provides
    fun provideDeleteCommentUseCase(
        commentService: CommentService
    ) = DeleteCommentUseCase(commentService)

    @Provides
    fun provideGetCommentListUseCase(
        commentService: CommentService
    ) = GetCommentListUseCase(commentService)
}