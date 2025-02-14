package com.capgemini.profiler.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val LOGIN_STATE_KEY = booleanPreferencesKey("login_state")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val PASSWORD_KEY = stringPreferencesKey("password")
    }

    // Save login state
    suspend fun saveLoginState(isLoggedIn: Boolean, username: String, password: String) {
        dataStore.edit { preferences ->
            preferences[LOGIN_STATE_KEY] = isLoggedIn
            preferences[USERNAME_KEY] = username
            preferences[PASSWORD_KEY] = password
        }
    }

    // Retrieve login state
    val loginStateFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[LOGIN_STATE_KEY] ?: false
    }

    // Retrieve username and password
    val userCredentialsFlow: Flow<Pair<String, String>> = dataStore.data.map { preferences ->
        val username = preferences[USERNAME_KEY] ?: ""
        val password = preferences[PASSWORD_KEY] ?: ""
        Pair(username, password)
    }

    // Clear DataStore on logout
    suspend fun clearLoginState() {
        dataStore.edit { it.clear() }
    }
}