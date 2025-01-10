package com.etalon.shashin

import android.content.Context
import android.content.SharedPreferences


class Prefferences (private var cont: Context) {
    private var sharedPreferences: SharedPreferences? = null
    private val PREFS = "PREFS"
    private val KEY = "KEY"


     fun insertCategory(category: String){
        sharedPreferences =
            cont.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        with(sharedPreferences?.edit()) {
            this?.putString(KEY, category)
            this?.apply()
        }
    }

     fun getCategory():String{
        sharedPreferences =
            cont.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val category =sharedPreferences?.getString(KEY, "category")
        return category!!
    }
}