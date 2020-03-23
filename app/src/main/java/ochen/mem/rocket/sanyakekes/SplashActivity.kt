package ochen.mem.rocket.sanyakekes

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import com.yandex.metrica.YandexMetricaDefaultValues
import ru.mail.aslanisl.mobpirate.MobPirate
import ochen.mem.rocket.sanyakekes.Constants.adsShow
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity(), AdMobFullscreenManager.AdMobFullscreenDelegate {
    internal var fullscreenManager: AdMobFullscreenManager? = null
    private var privatePoliceBtn: Button? = null
    internal var privacyPoliceClicked = false
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    private val adManager: AdMobFullscreenManager?
        get() {
            if (fullscreenManager == null) {
                configureManager()
            }
            return fullscreenManager
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flash_screen)
        signInAndInitUser(intent)
        privacyPoliceClicked = false
        privatePoliceBtn = findViewById(R.id.privacyPoliceBtn)
        val htmlTaggedString = "<u>" + getString(R.string.privacy_police) + "</u>"
        val textSpan = android.text.Html.fromHtml(htmlTaggedString)
        privatePoliceBtn!!.text = textSpan
        privatePoliceBtn!!.setOnClickListener {
            privacyPoliceClicked = true
            startActivity(Intent(this@SplashActivity, PrivacyPoliceActivity::class.java))
        }

        Log.i("CheckAdsBill", "onCreate")

        val canGoNext = MutableLiveData<Boolean>()
        canGoNext.observe(this, Observer {
            if(it){
                goNext()
            }
        })

        Thread{
            TimeUnit.SECONDS.sleep(2)
            canGoNext.postValue(true)
        }.start()
    }

    private fun startMainActivityWithDefaultConsent() {
        startActivity(MainActivity.getIntent(this, true))
    }

    private fun signInAndInitUser(intent: Intent) {
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        MobPirate.getInstance().getTargetUrl(this, intent)
        onRefferrer()
    }

    private fun setPirateUser() {
        if (MobPirate.getInstance().clientId != null && MobPirate.getInstance().clientId != "") {
            Log.d("traffic_id: ", MobPirate.getInstance().clientId)
            mFirebaseAnalytics!!.setUserProperty("traffic_id", MobPirate.getInstance().clientId)
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, MobPirate.getInstance().clientId)
            bundle.putString(FirebaseAnalytics.Param.MEDIUM, MobPirate.getInstance().clientId)
            bundle.putString(FirebaseAnalytics.Param.SOURCE, MobPirate.getInstance().clientId)
            bundle.putString(FirebaseAnalytics.Param.ACLID, MobPirate.getInstance().clientId)
            bundle.putString(FirebaseAnalytics.Param.CONTENT, MobPirate.getInstance().clientId)
            bundle.putString(FirebaseAnalytics.Param.CP1, MobPirate.getInstance().clientId)
            bundle.putString(FirebaseAnalytics.Param.VALUE, MobPirate.getInstance().clientId)
            mFirebaseAnalytics!!.logEvent("traffic_id", bundle)
            mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle)
            mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.CAMPAIGN_DETAILS, bundle)

        }
    }

    private fun onRefferrer() {
        var client = InstallReferrerClient.newBuilder(this).build()
        client.startConnection(object : InstallReferrerStateListener {
            override fun onInstallReferrerSetupFinished(responseCode: Int) {
                when(responseCode){
                    InstallReferrerClient.InstallReferrerResponse.OK -> Toast.makeText(this@SplashActivity, "Ok, ${client.installReferrer.installReferrer}", Toast.LENGTH_LONG).show()
                    InstallReferrerClient.InstallReferrerResponse.DEVELOPER_ERROR -> Toast.makeText(this@SplashActivity, "DEVELOPER_ERROR", Toast.LENGTH_LONG).show()
                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> Toast.makeText(this@SplashActivity, "FEATURE_NOT_SUPPORTED", Toast.LENGTH_LONG).show()
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED -> Toast.makeText(this@SplashActivity, "SERVICE_DISCONNECTED", Toast.LENGTH_LONG).show()
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> Toast.makeText(this@SplashActivity, "SERVICE_UNAVAILABLE", Toast.LENGTH_LONG).show()
                }
            }

            override fun onInstallReferrerServiceDisconnected() {
                Toast.makeText(this@SplashActivity, "onInstallReferrerServiceDisconnected", Toast.LENGTH_LONG).show()
            }
        } )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun configureManager() {
        if (fullscreenManager == null) {
            fullscreenManager = AdMobFullscreenManager(this, this)
        } else {
            fullscreenManager!!.reloadAd()
        }
    }

    override fun ADLoaded() {
        if (adManager!!.tryingShowDone) {
            adManager!!.showAdd()
        }
    }

    override fun ADIsClosed() {
        if (adManager!!.tryingShowDone) {
            goNext()
        }
    }

    override fun onResume() {
        privacyPoliceClicked = false
        adManager!!.completed()
        super.onResume()
        //getAdManager().completed();
    }

    fun srtatApp() {
        Log.i("CheckAdsBill", adsShow.toString())

        //PopUpAds.ShowInterstitialAds(getApplicationContext());
        // ConsentInformation consentInformation = ConsentInformation.getInstance(this);

        /*String[] publisherIds = {"pub-0123456789012345"};
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                if (ConsentInformation.getInstance(SplashActivity.this).isRequestLocationInEeaOrUnknown()) {
                    //startActivity(new Intent(SplashActivity.this, GDPRActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, GDPRActivity.class));
                }
            }

            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                startActivity(new Intent(SplashActivity.this, GDPRActivity.class));
            }
        });
        */


        goNext()
    }

    internal fun goNext() {
        if (!privacyPoliceClicked) {
            val i = Intent(applicationContext, MainActivity::class.java)
            //startActivity(i)
            //finish()
        }
    }

    companion object {
        private val AD_FREE = "noads"
    }
}