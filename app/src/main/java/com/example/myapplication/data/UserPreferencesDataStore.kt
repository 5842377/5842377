package com.example.myapplication.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferencesDataStore(private val context: Context) {

    private val MESSAGE_KEY = stringPreferencesKey("grad_message")

    val graduationMessage = context.dataStore.data.map { prefs ->
        prefs[MESSAGE_KEY] ?: ""
    }

    suspend fun saveMessage(message: String) {
        context.dataStore.edit { prefs ->
            prefs[MESSAGE_KEY] = message
        }
    }
}