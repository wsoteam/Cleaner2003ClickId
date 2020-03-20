package ochen.mem.rocket.sanyakekes.AAA

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import ochen.mem.rocket.sanyakekes.R
import ochen.mem.rocket.sanyakekes.Volume.PhoneBoosterFrag
import ochen.mem.rocket.sanyakekes.utils.PreferencesProvider

/**
 * Created by intag pc on 3/2/2017.
 */

class Alarm_Booster : BroadcastReceiver() {

    //todo Prefs
    override fun onReceive(context: Context, intent: Intent) {

        PreferencesProvider.getInstance().edit()
                .putString("booster", "1")
                .apply()

        try {
            PhoneBoosterFrag.setButtonText(R.string.optimize)
        } catch (e: Exception) {

        }

    }
}
