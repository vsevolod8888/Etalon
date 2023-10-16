package com.etalon.shashin

import android.content.Context
import android.content.SharedPreferences


class Prefferences (var cont: Context) {
    var sharedPreferences: SharedPreferences? = null
    val PREFS = "PREFS"
    val KEY = "KEY"


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