package com.sharkdroid.thenewsapp.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.sharkdroid.thenewsapp.domain.manager.LocalUserManager
import com.sharkdroid.thenewsapp.util.Constant
import com.sharkdroid.thenewsapp.util.Constant.APP_ENTRY
import com.sharkdroid.thenewsapp.util.Constant.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImp(private val context: Context ):LocalUserManager {
    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY]=true //accessing the preferences key to assign value
        }
    }

    override fun readAppEntry(): Flow<Boolean> {

        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.APP_ENTRY]?:false // not assigning false.
        }
    }
}

private  val Context.dataStore:DataStore<Preferences>by preferencesDataStore(name=USER_SETTINGS)

private object PreferenceKeys{

    val APP_ENTRY= booleanPreferencesKey(name= Constant.APP_ENTRY)
}