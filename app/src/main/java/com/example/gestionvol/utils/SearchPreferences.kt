package com.example.gestionvol.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "search_preferences")

class SearchPreferences(private val context: Context) {
    companion object {
        private val SEARCH_QUERY = stringPreferencesKey("search_query")
    }

    val searchQuery: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[SEARCH_QUERY] ?: ""
    }

    suspend fun saveSearchQuery(query: String) {
        context.dataStore.edit { preferences ->
            preferences[SEARCH_QUERY] = query
        }
    }
}