package com.example.notebook.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.notebook.data.local.DataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GroupPreferencesRepository(context: Context) {
    private val dataStore = context.DataStore

    private companion object {
        val GroupList =
            stringPreferencesKey("group_list")
    }

    private val gson = Gson()

    val groupFlow: Flow<List<String>> =
        dataStore.data.map {preferences ->
            val json = preferences[GroupList]
            if (json.isNullOrEmpty()) {

                listOf("全部")

            } else {

                val type = object : TypeToken<List<String>>() {}.type

                gson.fromJson(json, type)

            }
        }

    suspend fun addGroup(group: String) {
        dataStore.edit { preferences ->
            val json = preferences[GroupList]

            val type = object : TypeToken<List<String>>() {}.type

            val groups = if (json.isNullOrEmpty()) {
                listOf("全部")
            } else {
                gson.fromJson<List<String>>(json, type)
            }

            if (group !in groups) {
                preferences[GroupList] =
                    gson.toJson(groups + group)
            }
        }
    }
}