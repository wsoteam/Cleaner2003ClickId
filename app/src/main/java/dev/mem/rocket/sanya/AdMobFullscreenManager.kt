package dev.mem.rocket.sanya

import android.content.Context
import android.util.Log

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd


class AdMobFullscreenManager(private val context: Context?, delegate: AdMobFullscreenDelegate?) {

    private var countRequestAd: Int = 0

    private var delegate: AdMobFullscreenDelegate? = null
    var extrasInt: Long = -1
    var tryingShowDone = false
    private var adMobState = AdMobState.Loading
    private val mInterstitialAd: InterstitialAd

    enum class AdMobState {
        Loaded,
        Loading,
        NoAd
    }


    init {
        mInterstitialAd = InterstitialAd(context)
        configure(delegate)
    }

    private fun configure(delegate: AdMobFullscreenDelegate?) {
        mInterstitialAd.setAdUnitId(context?.getString(R.string.interstitial)!!)
        //mInterstitialAd.setAdUnitId("ca-app-pub-3149924354118474/9415209496");
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        this.delegate = delegate
        mInterstitialAd.setAdListener(object : AdListener() {
            override fun onAdLoaded() {
                Log.d("ExpressAppAdds", "onAdLoaded: ")
                countRequestAd = 0
                adMobState = AdMobState.Loaded
                if (this@AdMobFullscreenManager.delegate != null) {
                    this@AdMobFullscreenManager.delegate!!.ADLoaded()
                }
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                this@AdMobFullscreenManager.reloadAd()

                Log.d("ExpressAppAdds", "onAdFailedToLoad: $errorCode")
            }

            override fun onAdOpened() {
                Log.d("ExpressAppAdds", "onAdOpened: ")
                // Code to be executed when the ad is displayed.
            }

            override fun onAdLeftApplication() {
                Log.d("ExpressAppAdds", "onAdLeftApplication: ")
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                Log.d("ExpressAppAdds", "onAdClosed: ")
                if (this@AdMobFullscreenManager.delegate != null) {
                    this@AdMobFullscreenManager.delegate!!.ADIsClosed()
                }

                // Code to be executed when when the interstitial ad is closed.
            }
        })
    }

    fun showAdd(): Boolean {
        var b = false
        if(!SubscriptionProvider.hasSubscription()) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show()
                b = true
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.")
            }
        }
        return b
    }

    fun completed() {
        tryingShowDone = true
        if (adMobState == AdMobState.Loaded) {
            if (delegate != null) {
                delegate!!.ADLoaded()
            }
        } else if (adMobState == AdMobState.NoAd) {
            if (delegate != null) {
                delegate!!.ADIsClosed()
            }
        }

    }

    fun reloadAd() {
        countRequestAd++
        if (countRequestAd == MAX_REQUEST_AD) {
            adMobState = AdMobState.NoAd
            if (delegate != null) {
                delegate!!.ADIsClosed()
            }
            return
        }

        if (mInterstitialAd.isLoaded()) {
            adMobState = AdMobState.Loaded
            mInterstitialAd.show()
        } else if (!mInterstitialAd.isLoading()) {
            adMobState = AdMobState.Loading
            mInterstitialAd.loadAd(AdRequest.Builder().build())
        }
    }

    interface AdMobFullscreenDelegate {
        fun ADLoaded()
        fun ADIsClosed()
    }

    companion object {

        private val MAX_REQUEST_AD = 3
    }


}


