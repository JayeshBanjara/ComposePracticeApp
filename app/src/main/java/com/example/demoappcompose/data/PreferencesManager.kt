package com.example.demoappcompose.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class PreferencesManager @Inject constructor(@ApplicationContext private val appContext: Context) {

    companion object {
        val IS_LOGIN = intPreferencesKey("is_login")
        val TOKEN = stringPreferencesKey("login_token")
        val LOG_IN_LOG_ID = stringPreferencesKey("login_log_id")
        val USER_MOBILE_NUMBER = stringPreferencesKey("user_number")
        val USER_EMAIL = stringPreferencesKey("user_name")
        val USER_FULL_NAME = stringPreferencesKey("full_name")
        val USER_PROFILE_IMAGE = stringPreferencesKey("user_profile_image")
        val USER_ID = stringPreferencesKey("user_id")
        val ROLE_ID = stringPreferencesKey("role_id")

        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "my-pref")
    }

    suspend fun setLoggedIn(isLoggedIn: Int) {
        appContext.dataStore.edit { login ->
            login[IS_LOGIN] = isLoggedIn
        }
    }

    val getLoggedIn: Flow<Int?> = appContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[IS_LOGIN]
        }

    suspend fun setUserMobileNumber(mobileNumber: String) {
        appContext.dataStore.edit { preferences ->
            preferences[USER_MOBILE_NUMBER] = mobileNumber
        }
    }

    val getUserMobileNumber: Flow<String?> = appContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[USER_MOBILE_NUMBER]
        }

    suspend fun setUserProfileImage(imageUrl: String) {
        appContext.dataStore.edit { preferences ->
            preferences[USER_PROFILE_IMAGE] = imageUrl
        }
    }

    val getUserProfileImage: Flow<String?> = appContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[USER_PROFILE_IMAGE]
        }

    suspend fun setEmail(email: String) {
        appContext.dataStore.edit { mEmail ->
            mEmail[USER_EMAIL] = email
        }
    }

    val getEmail: Flow<String?> = appContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[USER_EMAIL]
        }

    suspend fun setUserFullName(fullName: String) {
        appContext.dataStore.edit { mFullName ->
            mFullName[USER_FULL_NAME] = fullName
        }
    }

    val getUserFullName: Flow<String?> = appContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[USER_FULL_NAME]
        }

    suspend fun setToken(token: String) {
        appContext.dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    val getToken: Flow<String?> = appContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }

        .map { preferences ->
            preferences[TOKEN]
        }

    suspend fun setLoginLogId(loginLogId: String) {
        appContext.dataStore.edit { preferences ->
            preferences[LOG_IN_LOG_ID] = loginLogId
        }
    }

    val getLoginLogId: Flow<String?> = appContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[LOG_IN_LOG_ID]
        }

    suspend fun setUserId(userId: String) {
        appContext.dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    val getUserId: Flow<String?> = appContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[USER_ID]
        }

    suspend fun setRoleId(roleId: String) {
        appContext.dataStore.edit { preferences ->
            preferences[ROLE_ID] = roleId
        }
    }

    val getRoleId: Flow<String?> = appContext.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emptyPreferences()
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[ROLE_ID]
        }

    suspend fun clearData() {
        appContext.dataStore.edit {
            it.clear()
        }
    }

}