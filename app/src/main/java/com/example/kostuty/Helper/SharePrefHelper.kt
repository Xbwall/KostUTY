package com.example.kostuty.Helper

import android.content.Context
import android.content.SharedPreferences

class SharePrefHelper(context: Context) {

    private val PREF_NAME = "shareprefLogin"
    private val sharePref: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharePref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        editor = sharePref.edit()
    }

    //STRING
    fun put(key: String, value: String){
        editor.putString(key, value).apply()
    }

    fun getString(key: String ): String?{
        return sharePref.getString(key, null)
    }

}