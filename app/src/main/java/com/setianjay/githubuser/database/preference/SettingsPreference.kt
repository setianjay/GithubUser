package com.setianjay.githubuser.database.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsPreference private constructor(private val dataStore: DataStore<Preferences>) {
    private val themeKey = booleanPreferencesKey("theme_key")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean){
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingsPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingsPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingsPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}