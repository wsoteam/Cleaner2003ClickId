package dev.mem.rocket.sanya.lang

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager

import java.util.Locale

import dev.mem.rocket.sanya.MainActivity

class BetweenActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Version of auto language selection with remembering of user's choice if his device has unsupported language
        /*Context context = getApplicationContext();
        String deviceLang;
        // Get current device language
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            deviceLang = getResources().getConfiguration().getLocales().getInstance(0).getLanguage();
        else
            deviceLang = context.getResources().getConfiguration().locale.getLanguage();

        // If language hasn't been set yet or varies with previous remembered
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).
                getString("PREVLANG", "defaultStringIfNothingFound").equals("defaultStringIfNothingFound") ||
                !deviceLang.equals(PreferenceManager.getDefaultSharedPreferences(context).getString("PREVLANG", "defaultPrevLang")))
        {
            if(deviceLang.equals("ar") || deviceLang.equals("en") || deviceLang.equals("ru") || deviceLang.equals("fr") ||
                    deviceLang.equals("de") || deviceLang.equals("it") || deviceLang.equals("pt") || deviceLang.equals("es"))
            {
                Resources res = context.getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = getResources().getConfiguration();
                conf.setLocale(new Locale(deviceLang));

                PreferenceManager.getDefaultSharedPreferences(context).edit().putString("PREVLANG", deviceLang).apply();
                final SharedPreferences settings = PreferenceManager
                        .getDefaultSharedPreferences(context);
                res.updateConfiguration(conf, dm);
                settings.edit().putString(SettingsFragment.LANGUAGE_SETTING, deviceLang).apply();

                // Refresh the app
                Intent refresh = new Intent(context, MainActivity.class);
                this.startActivity(refresh);
                this.setResult(SettingsFragment.LANGUAGE_CHANGED);
                this.finish();
            }
            // If language isn't supported then ask to choose some of the supported languages
            else {
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString("PREVLANG", deviceLang).apply();
                LanguagesDialogFirst languagesDialog = new LanguagesDialogFirst();
                languagesDialog.show(getFragmentManager(), "LangFuagesDialogFragment");
                languagesDialog.setCancelable(false);
            }
        }*/

        val context = applicationContext
        var deviceLang: String
        // Get current device language
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            deviceLang = resources.configuration.locales.get(0).language
        else
            deviceLang = context.resources.configuration.locale.language

        if (deviceLang != "ar" && deviceLang != "en" && deviceLang != "ru" && deviceLang != "fr" &&
                deviceLang != "de" && deviceLang != "it" && deviceLang != "pt" && deviceLang != "es")
            deviceLang = "en"

        if (deviceLang != PreferenceManager.getDefaultSharedPreferences(applicationContext).getString("PREVLANG", "defaultStringIfNothingFound")) {
            val res = context.resources
            val dm = res.displayMetrics
            val conf = resources.configuration
            conf.setLocale(Locale(deviceLang))

            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("PREVLANG", deviceLang).apply()
            val settings = PreferenceManager
                    .getDefaultSharedPreferences(context)
            res.updateConfiguration(conf, dm)
            settings.edit().putString(SettingsFragment.LANGUAGE_SETTING, deviceLang).apply()

            // Refresh the app
            val refresh = Intent(context, MainActivity::class.java)
            this.startActivity(refresh)
            this.setResult(SettingsFragment.LANGUAGE_CHANGED)
            this.finish()

        } else {
            val i = Intent(applicationContext, MainActivityLauncher::class.java)
            startActivity(i)
            finish()
        }

    }
}
