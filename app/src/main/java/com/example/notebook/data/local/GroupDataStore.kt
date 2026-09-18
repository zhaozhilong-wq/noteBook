package com.example.notebook.data.local

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.DataStore by preferencesDataStore(
    name = "group_preferences"
)