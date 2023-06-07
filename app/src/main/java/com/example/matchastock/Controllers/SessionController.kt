package com.example.matchastock.Controllers

import android.content.Context
import android.content.SharedPreferences

class SessionController private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyAppPref", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_ID = "idUser"

        @Volatile
        private var instance: SessionController? = null

        fun getInstance(context: Context): SessionController {
            return instance ?: synchronized(this) {
                instance ?: SessionController(context.applicationContext).also { instance = it }
            }
        }
    }

    fun saveUserInfo(idUser: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ID, idUser)

        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.contains(KEY_ID)
    }

    fun getId(): String? {
        return sharedPreferences.getString(KEY_ID, null)
    }


    fun clearSession() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}