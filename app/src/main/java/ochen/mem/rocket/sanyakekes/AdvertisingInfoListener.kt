package ochen.mem.rocket.sanyakekes

import com.google.android.gms.ads.identifier.AdvertisingIdClient

interface AdvertisingInfoListener {

    fun onInfoReceived(info: AdvertisingIdClient.Info)

}