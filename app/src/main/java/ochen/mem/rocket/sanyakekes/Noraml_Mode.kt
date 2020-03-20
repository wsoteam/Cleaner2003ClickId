package ochen.mem.rocket.sanyakekes

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
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
import com.google.android.gms.ads.AdRequest

import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent

import ochen.mem.rocket.sanyakekes.PPP.PowerSaving_Complition

import ochen.mem.rocket.sanyakekes.Constants.adsBatterySaver
import ochen.mem.rocket.sanyakekes.Constants.adsShow
import ochen.mem.rocket.sanyakekes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.banner_layout.adView
import kotlinx.android.synthetic.main.revert_to_normal.*

/**
 * Created by intag pc on 2/21/2017.
 */

class Noraml_Mode : Activity(), AdMobFullscreenManager.AdMobFullscreenDelegate {


    //todo Prefs
    internal var check = 0
    internal var fullscreenManager: AdMobFullscreenManager? = null
//    private var mAdView: AdView? = null

    private val adManager: AdMobFullscreenManager?
        get() {
            if (fullscreenManager == null) {
                configureManager()
            }
            return fullscreenManager
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.revert_to_normal)

        val adRequest = AdRequest.Builder().build()
        if(!SubscriptionProvider.hasSubscription()) {
            adView!!.loadAd(adRequest)
        }

        ///// Call to Intersticial load

        if (adsBatterySaver && adsShow) {
        }
        adsBatterySaver = !adsBatterySaver


        //// DEcoView Library For Progress Completion a circle Drrawn using this library


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

                    //                    final InterstitialAd mInterstitialAd = new InterstitialAd(getApplicationContext());
                    //                    mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
                    //                    AdRequest adRequestInter = new AdRequest.Builder().build();
                    //                    mInterstitialAd.setAdListener(new AdListener() {
                    //                        @Override
                    //                        public void onAdLoaded() {
                    //                            mInterstitialAd.show();
                    //                        }
                    //                    });
                    //                    mInterstitialAd.loadAd(adRequestInter);

                } else if (v1 >= 50 && v1 < 75) {

                } else if (v1 >= 75 && v1 < 90) {

                } else if (v1 >= 90 && v1 <= 100) {

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

                if (adsShow) {
                    adManager!!.completed()
                } else {
                    check = 1
                    youDesirePermissionCode(this@Noraml_Mode)
                    //                enablesall();
                    PreferencesProvider.getInstance().edit()
                            .putString("mode", "0")
                            .apply()
                }
                //                bottom.setText("Found");
                //                top.setText("Storage");
                //                Random ran3 = new Random();
                //                ramperct.setText(ran3.nextInt(40) + 20+"%");


            }
        }).build())
    }

    fun enablesall() {


        /// activate and release all system services


        //        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //        if (!mBluetoothAdapter.isEnabled()) {
        //            mBluetoothAdapter.enable();
        //        }

        //        WifiManager wifiManager = (WifiManager) getApplication().getSystemService(Context.WIFI_SERVICE);
        //
        //
        //        boolean wifiEnabled = wifiManager.isWifiEnabled();
        //        if(wifiEnabled)
        //        {
        //            wifiManager.setWifiEnabled(false);
        //        }

        PowerSaving_Complition.setAutoOrientationEnabled(applicationContext, true)

        Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 255)

        ContentResolver.setMasterSyncAutomatically(true)

    }

    override fun onBackPressed() {
        //        super.onBackPressed();
    }


    fun youDesirePermissionCode(context: Activity) {

        //// Run time permission for marshmallow users

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
            enablesall()

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
            enablesall()

            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do your code

            //            Toast.makeText(getApplicationContext(),"onRequestPermissionsResult",Toast.LENGTH_LONG).show();

            enablesall()

            finish()
        }

    }


    override fun onResume() {
        super.onResume()
        if (check == 1) {
            try {
                enablesall()
            } catch (e: Exception) {
                finish()
            }

            finish()
        }
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
            check = 1
            youDesirePermissionCode(this@Noraml_Mode)
            //                enablesall();
            PreferencesProvider.getInstance().edit()
                    .putString("mode", "0")
                    .apply()
        }
    }


}
