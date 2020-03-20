package dev.mem.rocket.sanya.utils

import android.content.Context
import dev.mem.rocket.sanya.MyApp

object PreferencesProvider{

    private val preferences = MyApp.getInstance().getSharedPreferences("waseem", Context.MODE_PRIVATE)

    fun getInstance() = preferences
}