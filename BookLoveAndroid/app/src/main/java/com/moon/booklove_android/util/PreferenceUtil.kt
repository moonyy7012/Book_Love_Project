package com.moon.booklove_android.util

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

}
