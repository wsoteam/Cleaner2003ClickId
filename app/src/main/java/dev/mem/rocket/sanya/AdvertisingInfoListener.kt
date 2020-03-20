package dev.mem.rocket.sanya

import com.google.android.gms.ads.identifier.AdvertisingIdClient

interface AdvertisingInfoListener {

    fun onInfoReceived(info: AdvertisingIdClient.Info)

}