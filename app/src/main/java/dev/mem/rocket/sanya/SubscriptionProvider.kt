package dev.mem.rocket.sanya

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.android.billingclient.api.*
import dev.mem.rocket.sanya.Config.Companion.HAS_SUBSCRIPTION
import dev.mem.rocket.sanya.inapp.InAppCallback

object SubscriptionProvider : PurchasesUpdatedListener, BillingClientStateListener {

    lateinit private var playStoreBillingClient: BillingClient
    private lateinit var preferences: SharedPreferences

    private const val SUBSCRIPTION_ID = "no_ads_599"
    private const val IS_APPROVED = "is_approved"
    private var inAppCallback: InAppCallback? = null


    fun init(context: Context) {
        preferences = context.getSharedPreferences("subscription", Context.MODE_PRIVATE)
        playStoreBillingClient = BillingClient.newBuilder(context.applicationContext)
            .enablePendingPurchases() // required or app will crash
            .setListener(this).build()
        connectToPlayBillingService()
    }

    private fun connectToPlayBillingService(): Boolean {
        if (!playStoreBillingClient.isReady) {
            playStoreBillingClient.startConnection(this)
            return true
        }
        return false
    }

    override fun onPurchasesUpdated(billingResult: BillingResult?, purchases: MutableList<Purchase>?) {
        if (billingResult!!.responseCode == BillingClient.BillingResponseCode.OK) {
            inAppCallback?.trialSucces()
        }

    }

    override fun onBillingServiceDisconnected() {

    }

    override fun onBillingSetupFinished(billingResult: BillingResult) {
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            var hasSubscription = false
            val result = playStoreBillingClient.queryPurchases(BillingClient.SkuType.SUBS)
            if (result.purchasesList.size > 0) {
                hasSubscription = true

            }
            preferences.edit().putBoolean(HAS_SUBSCRIPTION, hasSubscription).apply()
        }
    }

    fun hasSubscription() = preferences.getBoolean(HAS_SUBSCRIPTION, false)

    fun setSuccesSubscription() = preferences.edit().putBoolean(HAS_SUBSCRIPTION, true).commit()

    fun startSubscription(activity: Activity) {
        val params = SkuDetailsParams.newBuilder().setSkusList(arrayListOf(SUBSCRIPTION_ID))
            .setType(BillingClient.SkuType.SUBS).build()
        playStoreBillingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    if (skuDetailsList.orEmpty().isNotEmpty()) {
                        skuDetailsList.forEach {
                            val perchaseParams = BillingFlowParams.newBuilder().setSkuDetails(it)
                                .build()
                            playStoreBillingClient.launchBillingFlow(activity, perchaseParams)
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    fun startChoiseSub(activity: Activity, id: String, callback: InAppCallback) {
        inAppCallback = callback
        val params = SkuDetailsParams.newBuilder().setSkusList(arrayListOf(SUBSCRIPTION_ID))
            .setType(BillingClient.SkuType.SUBS).build()
        playStoreBillingClient.querySkuDetailsAsync(params) { billingResult, skuDetailsList ->
            when (billingResult.responseCode) {
                BillingClient.BillingResponseCode.OK -> {
                    if (skuDetailsList.orEmpty().isNotEmpty()) {
                        skuDetailsList.forEach {
                            val perchaseParams = BillingFlowParams.newBuilder().setSkuDetails(it)
                                .build()
                            playStoreBillingClient.launchBillingFlow(activity, perchaseParams)
                        }
                    }
                }
                else -> {
                }
            }
        }
    }


}
