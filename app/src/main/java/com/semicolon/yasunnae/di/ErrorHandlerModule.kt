package com.semicolon.yasunnae.di

import com.semicolon.data.base.ErrorHandlerImpl
import com.semicolon.domain.base.ErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ErrorHandlerModule {

    @Provides
    fun provideErrorHandler(): ErrorHandler =
        ErrorHandlerImpl()
}