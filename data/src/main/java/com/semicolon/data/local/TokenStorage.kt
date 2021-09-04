package com.semicolon.data.local

interface TokenStorage {

    fun saveToken(accessToken: String, refreshToken: String)

    fun saveAccessToken(accessToken: String)

    fun saveRefreshToken(refreshToken: String)

    fun getAccessToken(): String

    fun getRefreshToken(): String

    fun deleteToken()
}