package com.ilhmdhn.storyapp.model.user

import android.content.Context
import com.ilhmdhn.storyapp.data.remote.response.LoginResult

internal class UserPreference(context: Context) {
    companion object{
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val USER_ID = "useri_id"
        private const val TOKEN = "token"
        private const val IS_LOGIN = "is_login"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setUser(data: LoginResult){
        val editor = preferences.edit()
        editor.putString(NAME, data.name)
        editor.putString(USER_ID, data.userId)
        editor.putString(TOKEN, data.token)
        editor.putBoolean(IS_LOGIN, true)
        editor.apply()
    }

    fun getUser(): UserModel{
        val model = UserModel()
        model.name = preferences.getString(NAME, "")
        model.userId = preferences.getString(USER_ID, "")
        model.token = preferences.getString(TOKEN, "")
        model.isLogin = preferences.getBoolean(IS_LOGIN, false)
        return model
    }

    fun delUser(){
        preferences.edit().clear().apply()
    }
}