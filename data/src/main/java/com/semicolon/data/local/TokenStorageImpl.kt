package com.semicolon.data.local

import android.content.Context
import androidx.preference.PreferenceManager

class TokenStorageImpl(
    private val context: Context
) : TokenStorage {

    override fun saveToken(accessToken: String, refreshToken: String) {
        saveAccessToken(accessToken)
        saveRefreshToken(refreshToken)
    }

    override fun saveAccessToken(accessToken: String) =
        getSharedPreference().edit().let {
            it.putString(TokenKey.ACCESS_TOKEN_KEY, "Bearer $accessToken")
            it.apply()
        }


    override fun saveRefreshToken(refreshToken: String) =
        getSharedPreference().edit().let {
            it.putString(TokenKey.REFRESH_TOKEN_KEY, "Bearer $refreshToken")
            it.apply()
        }

    override fun getAccessToken(): String =
        getSharedPreference().getString(TokenKey.ACCESS_TOKEN_KEY, "")!!

    override fun getRefreshToken(): String =
        getSharedPreference().getString(TokenKey.REFRESH_TOKEN_KEY, "")!!

    override fun deleteToken() =
        getSharedPreference().edit().let {
            it.remove(TokenKey.ACCESS_TOKEN_KEY)
            it.remove(TokenKey.REFRESH_TOKEN_KEY)
            it.apply()
        }

    private fun getSharedPreference() =
        PreferenceManager.getDefaultSharedPreferences(context)

    object TokenKey {
        const val ACCESS_TOKEN_KEY = "ACCESS_TOKEN"
        const val REFRESH_TOKEN_KEY = "REFRESH_TOKEN"
    }
}