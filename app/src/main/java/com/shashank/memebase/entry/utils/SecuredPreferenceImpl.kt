package com.shashank.memebase.entry.utils

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.shashank.memebase.globals.Constants.ApiProperties.ACCESS_TOKEN_KEY
import javax.inject.Inject

class SecuredPreferenceImpl @Inject constructor(private var sharedPreferences: SharedPreferences, var context: Context) : SecuredPreference {

    override suspend fun generateSecuredPreference() {

        try {
            val customSpec = KeyGenParameterSpec.Builder(
                "master_key",
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .build()

            val keyAlias: String = MasterKeys.getOrCreate(customSpec)

            sharedPreferences = EncryptedSharedPreferences.create(
                "TaskySecuredPreference",
                keyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun setAccessToken(accessToken: String?) {
        generateSecuredPreference()
        sharedPreferences.edit().putString(ACCESS_TOKEN_KEY, accessToken).apply()
    }

    override suspend fun getAccessToken(): String {
        generateSecuredPreference()
        return sharedPreferences.getString(ACCESS_TOKEN_KEY, "") as String
    }
}