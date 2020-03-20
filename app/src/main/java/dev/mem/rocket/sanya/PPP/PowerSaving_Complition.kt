package dev.mem.rocket.sanya.PPP

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.google.android.gms.ads.AdRequest

import dev.mem.rocket.sanya.AdMobFullscreenManager
import dev.mem.rocket.sanya.R

import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent

import dev.mem.rocket.sanya.Constants.adsShow
import dev.mem.rocket.sanya.SubscriptionProvider
import kotlinx.android.synthetic.main.banner_layout.adView
import kotlinx.android.synthetic.main.powersaving_completion.*

/**
 * Created by intag pc on 2/22/2017.
 */

class PowerSaving_Complition : Activity(), AdMobFullscreenManager.AdMobFullscreenDelegate {

    internal var check = 0
    private var fullscreenManager: AdMobFullscreenManager? = null
//    private var mAdView: AdView? = null

    private val adManager: AdMobFullscreenManager?
        get() {
            if (fullscreenManager == null) {
                configureManager()
            }
            return fullscreenManager
        }


    /// Power Saving Mode is Applied Compeltion Indicator Animation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.powersaving_completion)

        val adRequest = AdRequest.Builder().build()
        if(!SubscriptionProvider.hasSubscription()) {
            adView!!.loadAd(adRequest)
        }





        //        dynamicArcView2.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
        //                .setRange(0, 100, 0)
        //                .setInterpolator(new AccelerateInterpolator())
        //                .build());

        dynamicArcView2.addSeries(SeriesItem.Builder(Color.parseColor("#27282D"))
                .setRange(0f, 100f, 100f)
                .setInitialVisibility(false)
                .setLineWidth(12f)
                .build())

        //Create data series track
        val seriesItem1 = SeriesItem.Builder(Color.parseColor("#27282D"))
                .setRange(0f, 100f, 0f)
                .setLineWidth(10f)
                .build()

        val seriesItem2 = SeriesItem.Builder(Color.parseColor("#FFFFFF"))
                .setRange(0f, 100f, 0f)
                .setLineWidth(10f)
                .build()
        //
        //        int series1Index = dynamicArcView2.addSeries(seriesItem1);
        val series1Index2 = dynamicArcView2.addSeries(seriesItem2)

        seriesItem2.addArcSeriesItemListener(object : SeriesItem.SeriesItemListener {
            override fun onSeriesItemAnimationProgress(v: Float, v1: Float) {


                val i = v1.toInt()
                completion.text = "$i%"

                if (v1 >= 10 && v1 < 50) {
                    ist.setTextColor(Color.parseColor("#FFFFFF"))
                    istpic.setImageResource(R.drawable.circle_white)

                } else if (v1 >= 50 && v1 < 75) {
                    sec.setTextColor(Color.parseColor("#FFFFFF"))
                    secpic.setImageResource(R.drawable.circle_white)
                } else if (v1 >= 75 && v1 < 90) {
                    thi.setTextColor(Color.parseColor("#FFFFFF"))
                    thipic.setImageResource(R.drawable.circle_white)
                } else if (v1 >= 90 && v1 <= 100) {
                    fou.setTextColor(Color.parseColor("#FFFFFF"))
                    foupic.setImageResource(R.drawable.circle_white)


                }


            }

            override fun onSeriesItemDisplayProgress(v: Float) {

            }
        })


        dynamicArcView2.addEvent(DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(0)
                .setDuration(0)
                .setListener(object : DecoEvent.ExecuteEventListener {
                    override fun onEventStart(decoEvent: DecoEvent) {
                        //                        bottom.setText("");
                        //                        top.setText("");
                        //                        centree.setText("Optimizing...");

                    }

                    override fun onEventEnd(decoEvent: DecoEvent) {

                    }

                })
                .build())

        dynamicArcView2.addEvent(DecoEvent.Builder(100f).setIndex(series1Index2).setDelay(1000).setListener(object : DecoEvent.ExecuteEventListener {
            override fun onEventStart(decoEvent: DecoEvent) {
                //                bottom.setText("");
                //                top.setText("");
                //                centree.setText("Optimizing...");
            }

            override fun onEventEnd(decoEvent: DecoEvent) {
                //                bottom.setText("Found");
                //                top.setText("Storage");
                //                Random ran3 = new Random();
                //                ramperct.setText(ran3.nextInt(40) + 20+"%");
                if (adsShow) {
                    adManager!!.completed()
                } else {
                    youDesirePermissionCode(this@PowerSaving_Complition)
                    closesall()
                    check = 1
                }
            }
        }).build())
    }

    fun closesall() {
        val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter.isEnabled) {
            mBluetoothAdapter.disable()
        }

        //        WifiManager wifiManager = (WifiManager) getApplication().getSystemService(Context.WIFI_SERVICE);
        //
        //
        //        boolean wifiEnabled = wifiManager.isWifiEnabled();
        //        if(wifiEnabled)
        //        {
        //            wifiManager.setWifiEnabled(false);
        //        }


        ContentResolver.setMasterSyncAutomatically(false)


        //


    }


    override fun onBackPressed() {
        //        super.onBackPressed();
    }


    fun youDesirePermissionCode(context: Activity) {
        val permission: Boolean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context)
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED
            //            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 30);
            //            setAutoOrientationEnabled(context, false);
        }
        if (permission) {
            //do your code
            Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 30)
            setAutoOrientationEnabled(context, false)

            finish()
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:" + context.packageName)
                context.startActivityForResult(intent, 1)
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_SETTINGS), 1)
            }
        }
    }

    //
    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && Settings.System.canWrite(this)) {
            Log.d("TAG", "CODE_WRITE_SETTINGS_PERMISSION success")


            //            Toast.makeText(getApplicationContext(),"onActivityResult",Toast.LENGTH_LONG).show();
            //do your code
            Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 30)
            setAutoOrientationEnabled(this, false)

            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do your code

            Toast.makeText(applicationContext, "onRequestPermissionsResult", Toast.LENGTH_LONG).show()

            Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 30)
            setAutoOrientationEnabled(this, false)

            finish()
        }

    }


    override fun onResume() {
        super.onResume()
        if (check == 1) {
            try {
                Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 30)
                setAutoOrientationEnabled(this, false)
            } catch (e: Exception) {
                finish()
            }

            finish()
        }
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
            youDesirePermissionCode(this@PowerSaving_Complition)
            closesall()
            check = 1
        }
    }

    companion object {

        fun setAutoOrientationEnabled(context: Context, enabled: Boolean) {
            Settings.System.putInt(context.contentResolver, Settings.System.ACCELEROMETER_ROTATION, if (enabled) 1 else 0)
        }
    }
}
