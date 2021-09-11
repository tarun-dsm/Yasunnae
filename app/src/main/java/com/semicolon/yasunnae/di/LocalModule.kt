package com.semicolon.yasunnae.di

import android.content.Context
import com.semicolon.data.local.TokenStorage
import com.semicolon.data.local.TokenStorageImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideTokenStorage(@ApplicationContext context: Context): TokenStorage =
        TokenStorageImpl(context)
}