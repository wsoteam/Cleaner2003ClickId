package ochen.mem.rocket.sanyakekes.utils

import android.content.Context
import ochen.mem.rocket.sanyakekes.MyApp

object PreferencesProvider{

    private val preferences = MyApp.getInstance().getSharedPreferences("waseem", Context.MODE_PRIVATE)

    fun getInstance() = preferences
}