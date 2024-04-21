package ru.trifonov.clubfirst.common.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SettingsData (
    private val cntx: Context?
) {
    var sp: SharedPreferences? = PreferenceManager.getDefaultSharedPreferences(cntx)
    init {

    }
    fun getToken(): String? {
        return sp!!.getString("token", "")
    }
    fun setToken(token: String){
        sp!!.edit().putString("token", token).apply()
    }

}