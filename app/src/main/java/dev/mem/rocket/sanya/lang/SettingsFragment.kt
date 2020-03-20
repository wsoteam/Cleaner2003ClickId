package dev.mem.rocket.sanya.lang


import android.os.Bundle
import android.preference.Preference
import android.preference.Preference.OnPreferenceClickListener
import android.preference.PreferenceFragment

import dev.mem.rocket.sanya.R

class SettingsFragment : PreferenceFragment(), OnPreferenceClickListener {

    override fun onResume() {
        super.onResume()

        activity.setTitle(R.string.action_settings)
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        val preferenceKey = if (preference != null) preference.key else ""

        return if (preferenceKey == getString(R.string.pref_key_language)) {
            handleLanguagePreferenceClick()
        } else false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)
        findPreference(getString(R.string.pref_key_language)).onPreferenceClickListener = this
    }

    private fun handleLanguagePreferenceClick(): Boolean {
        val languagesDialog = LanguagesDialog()
        languagesDialog.show(fragmentManager, "LanguagesDialogFragment")
        return true
    }

    companion object {
        val LANGUAGE_SETTING = "lang_setting"
        val LANGUAGE_CHANGED = 1000
    }

}
