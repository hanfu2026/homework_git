package com.sample.git.sample.utils

import android.content.Context

const val PREFERENCE_SHARED_PREF = "SHARED_PREF"
const val PREFERENCE_AUTH_CODE = "AUTH_CODE"
const val PREFERENCE_TOKEN_KEY = "TOKEN_KEY"
const val PREFERENCE_USERNAME_KEY = "USERNAME_KEY"

object PreferenceHelper {
    fun saveCode(context: Context, code: String){
        val sharedPref = context.getSharedPreferences(PREFERENCE_SHARED_PREF, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(PREFERENCE_AUTH_CODE, code)
            apply()
        }
    }

    fun saveToken(context: Context, token:String){
        val sharedPref = context.getSharedPreferences(PREFERENCE_SHARED_PREF, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(PREFERENCE_TOKEN_KEY, token)
            apply()
        }
    }

    fun saveAuthenticatedUser(context: Context, username: String){
        val sharedPref = context.getSharedPreferences(PREFERENCE_SHARED_PREF, Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(PREFERENCE_USERNAME_KEY, username)
            apply()
        }
    }

    fun getCode(context: Context): String {
        val sharedPref = context.getSharedPreferences(PREFERENCE_SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPref.getString(PREFERENCE_AUTH_CODE, "")!!
    }

    fun getToken(context: Context): String {
        val sharedPref = context.getSharedPreferences(PREFERENCE_SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPref.getString(PREFERENCE_TOKEN_KEY, "")!!
    }

    fun getAuthenticatedUsername(context: Context): String {
        val sharedPref = context.getSharedPreferences(PREFERENCE_SHARED_PREF, Context.MODE_PRIVATE)
        return sharedPref.getString(PREFERENCE_USERNAME_KEY, "")!!
    }

    fun clearSavedToken(context: Context) {
        saveToken(context, "")
        saveAuthenticatedUser(context, "")
    }
}