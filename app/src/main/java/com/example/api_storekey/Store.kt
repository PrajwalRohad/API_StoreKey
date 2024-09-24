package com.example.api_storekey

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class Store private constructor(private val context: Context) {

    private val sharedPreferences: SharedPreferences = createEncryptedSharedPreferences()

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: Store? = null

        // The key constant that will be encrypted and stored
        private const val KEY = "bhagbsdk"

        // Singleton access method
        fun getInstance(context: Context): Store {
            return instance ?: synchronized(this) {
                // Ensure that application context is used for the singleton
                instance ?: Store(context.applicationContext).also { instance = it }
            }
        }
    }

    init {
        // Encrypt and store the key when the class is created
        putKey()
    }

    // Function to encrypt and store the key in shared preferences
    private fun putKey() {
        sharedPreferences.edit().apply {
            putString("key", KEY)
//            putString("key2", KEY)
            apply() // Apply changes to sharedPreferences
        }
    }

    // Function to retrieve the encrypted key from shared preferences
    fun getKey(): String? {
        return sharedPreferences.getString("key", null)
    }

    // Helper function to create EncryptedSharedPreferences
    private fun createEncryptedSharedPreferences(): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}
