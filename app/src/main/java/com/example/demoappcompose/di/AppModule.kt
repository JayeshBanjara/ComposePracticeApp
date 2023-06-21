package com.example.demoappcompose.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val USER_PREFERENCES = "user_preferences"

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(

            /**
             * corruptionHandler (optional) — invoked if a CorruptionException is thrown by
             * the serializer when the data cannot be de-serialized which instructs DataStore
             * how to replace the corrupted data
             */
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),

            /**
             * migrations (optional) — a list of DataMigration for moving previous data into DataStore
             */
            migrations = listOf(SharedPreferencesMigration(appContext, USER_PREFERENCES)),

            /**
             * scope (optional) — the scope in which IO operations and transform functions will execute;
             * in this case, we’re reusing the same scope as the DataStore API default one
             */
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),

            /**
             * produceFile — generates the File object for Preferences DataStore based on the provided
             * Context and name, stored in this.applicationContext.filesDir + datastore/ subdirectory
             */
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }
}