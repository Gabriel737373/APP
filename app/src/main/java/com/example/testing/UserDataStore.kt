// Archivo: UserDataStore.kt
package com.example.testing

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Crea una única instancia de DataStore para toda la app
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_account")

class UserDataStore(private val context: Context) {

    // 1. Define las claves para guardar cada dato
    companion object {
        val USERNAME_KEY = stringPreferencesKey("user_username")
        val EMAIL_KEY = stringPreferencesKey("user_email")
        val PASSWORD_KEY = stringPreferencesKey("user_password")
    }

    // 2. Funciones para LEER los datos
    val usernameFlow: Flow<String?> = context.dataStore.data.map { it[USERNAME_KEY] }
    val emailFlow: Flow<String?> = context.dataStore.data.map { it[EMAIL_KEY] }
    val passwordFlow: Flow<String?> = context.dataStore.data.map { it[PASSWORD_KEY] }


    // 3. Función para GUARDAR todos los datos a la vez
    suspend fun saveUserAccount(username: String, email: String, password: String) {
        context.dataStore.edit { settings ->
            settings[USERNAME_KEY] = username
            settings[EMAIL_KEY] = email
            settings[PASSWORD_KEY] = password
        }
    }
}
