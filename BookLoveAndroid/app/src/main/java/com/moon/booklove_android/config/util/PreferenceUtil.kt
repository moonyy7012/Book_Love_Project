package com.moon.booklove_android.config.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("preference", Context.MODE_PRIVATE)

    fun getJWTAccess(): String? {
        return prefs.getString("jwtAccess", "")
    }

    fun setJWTAccess(jwtAccess: String) {
        val editor = prefs.edit()
        editor.putString("jwtAccess", jwtAccess)
        editor.apply()
    }

    fun getJWTRefresh(): String? {
        return prefs.getString("jwtRefresh", "")
    }

    fun setJWTRefresh(jwtRefresh: String) {
        val editor = prefs.edit()
        editor.putString("jwtRefresh", jwtRefresh)
        editor.apply()
    }

    fun getUserId(): String? {
        return prefs.getString("userId", "")
    }

    fun setUserId(userId: String) {
        val editor = prefs.edit()
        editor.putString("userId", userId)
        editor.apply()
    }

    fun getLoginType(): String? {
        return prefs.getString("loginType", "")
    }

    fun setLoginType(loginType: String) {
        val editor = prefs.edit()
        editor.putString("loginType", loginType)
        editor.apply()
    }
}
