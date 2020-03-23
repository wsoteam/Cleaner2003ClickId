package ochen.mem.rocket.sanyakekes

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Process
import android.webkit.WebView
import com.facebook.FacebookSdk
import com.facebook.FacebookSdk.setAutoLogAppEventsEnabled
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.ads.MobileAds
import com.rt.ModuleApplication
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import ru.mail.aslanisl.mobpirate.MobPirate

class MyApp : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"))
    }


    override fun onCreate() {
        super.onCreate()
        sInstance = this

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val process = getProcessName(this)
            if (packageName != process) {
                WebView.setDataDirectorySuffix(process)
            }
        }

        SubscriptionProvider.init(this)

        MobileAds.initialize(this, "ca-app-pub-3050564412171997~7487912829") //ca-app-pub-9387354664905418~6073119457
        // Создание расширенной конфигурации библиотеки.
        val config = YandexMetricaConfig.newConfigBuilder("06969962-a5ce-4a86-8e1c-b7d826be7ebd").build()
        // Инициализация AppMetrica SDK.
        YandexMetrica.activate(applicationContext, config)
        // Отслеживание активности пользователей.
        YandexMetrica.enableActivityAutoTracking(this)

    }


    private fun getProcessName(context: Context?): String? {
        if (context == null) return null
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (processInfo in manager.runningAppProcesses) {
            if (processInfo.pid == Process.myPid()) {
                return processInfo.processName
            }
        }
        return null
    }

    companion object {

        private lateinit var sInstance: MyApp

        fun getInstance(): MyApp {
            return sInstance
        }

        /**
         * Returns an instance of [MyApp] attached to the passed activity.
         */
        fun getInstance(activity: Activity): MyApp {
            return activity.application as MyApp
        }
    }

}