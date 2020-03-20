package dev.mem.rocket.sanya.BCJ

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import com.google.android.gms.ads.AdRequest

import dev.mem.rocket.sanya.AdMobFullscreenManager
import dev.mem.rocket.sanya.OOP.ApplicationsClass
import dev.mem.rocket.sanya.R
import dev.mem.rocket.sanya.Scan_Cpu_Apps
import dev.mem.rocket.sanya.Volume.CPUCoolerFrag

import com.skyfishjy.library.RippleBackground

import java.util.ArrayList

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

import dev.mem.rocket.sanya.Constants.adsShow
import dev.mem.rocket.sanya.SubscriptionProvider
import kotlinx.android.synthetic.main.banner_layout.adView
import kotlinx.android.synthetic.main.cpu_scanner.*


/**
 * Created by intag pc on 2/25/2017.
 */

class Cpu_Scanner : Activity(), AdMobFullscreenManager.AdMobFullscreenDelegate {


    ///// Scan Cpu For Power Consuming and Over heating ApplicationsClass

    lateinit var mAdapter: Scan_Cpu_Apps
    internal var app: List<ApplicationsClass>? = null
    internal var pm: PackageManager? = null
    private var fullscreenManager: AdMobFullscreenManager? = null
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
        setContentView(R.layout.cpu_scanner)

        val adRequest = AdRequest.Builder().build()
        if(!SubscriptionProvider.hasSubscription()) {
            adView!!.loadAd(adRequest)
        }

        app = ArrayList()

        val rotate = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 1500
        rotate.repeatCount = 3
        rotate.interpolator = LinearInterpolator()
        scann.startAnimation(rotate)


        val animation = TranslateAnimation(0.0f, 1000.0f, 0.0f, 0.0f)          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.duration = 5000  // animation duration
        animation.repeatCount = 0
        animation.interpolator = LinearInterpolator()// animation repeat count
        //        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        animation.fillAfter = true

        heart.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                heart.setImageResource(0)
                heart.setBackgroundResource(0)
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })



        recycler_view.itemAnimator = SlideInLeftAnimator()


        mAdapter = Scan_Cpu_Apps(CPUCoolerFrag.apps)
        val mLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.layoutManager = mLayoutManager
        recycler_view.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recycler_view.computeHorizontalScrollExtent()
        recycler_view.adapter = mAdapter
        //mAdapter.notifyDataSetChanged();

        killall()

        try {


            val handler1 = Handler()
            handler1.postDelayed({ add(getString(R.string.cpu_scanner1), 0) }, 0)

            val handler2 = Handler()
            handler2.postDelayed({
                remove(0)
                add(getString(R.string.cpu_scanner2), 1)
            }, 900)

            val handler3 = Handler()
            handler3.postDelayed({
                remove(0)
                add(getString(R.string.cpu_scanner3), 2)
            }, 1800)

            val handler4 = Handler()
            handler4.postDelayed({
                remove(0)
                add(getString(R.string.cpu_scanner4), 3)
            }, 2700)

            val handler5 = Handler()
            handler5.postDelayed({
                remove(0)
                add(getString(R.string.cpu_scanner4), 4)
            }, 3700)
            //
            val handler6 = Handler()
            handler6.postDelayed({
                remove(0)
                add(getString(R.string.cpu_scanner4), 5)
            }, 4400)

            val handler7 = Handler()
            handler7.postDelayed({
                add(getString(R.string.cpu_scanner4), 6)
                remove(0)

                val rippleBackground = findViewById<View>(R.id.content) as RippleBackground
                rippleBackground.startRippleAnimation()

                heart.setImageResource(0)
                heart.setBackgroundResource(0)
                cpu.setImageResource(R.drawable.green_circle)
                scann.setImageResource(R.drawable.task_complete)
                val anim = AnimatorInflater.loadAnimator(applicationContext, R.animator.flipping) as ObjectAnimator
                anim.target = scann
                anim.duration = 3000
                anim.start()

                rel.visibility = View.GONE

                cpucooler.setText(R.string.cooled_CPU_to)
                anim.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        heart.setImageResource(0)
                        heart.setBackgroundResource(0)
                    }

                    override fun onAnimationEnd(animation: Animator) {

                        rippleBackground.stopRippleAnimation()
                        if (adsShow) {
                            adManager!!.completed()
                        } else {
                            val handler6 = Handler()
                            handler6.postDelayed({
                                //                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5);

                                finish()
                            }, 1000)
                        }

                    }

                    override fun onAnimationCancel(animation: Animator) {

                    }

                    override fun onAnimationRepeat(animation: Animator) {

                    }
                })
            }, 5500)
            //
            //        final Handler handler8 = new Handler();
            //        handler8.postDelayed(new Runnable() {
            //            @Override
            //            public void run() {
            ////                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
            //                remove(0);
            //
            //
            //            }
            //        }, 8000);

        } catch (e: Exception) {

        }

    }


    fun add(text: String, position: Int) {


        //        int p=0 + (int)(Math.random() * ((packages.size() - 0) + 1));

        //        Drawable ico = null;

        //        ApplicationsClass item=new ApplicationsClass();

        //        String packageName = packages.getInstance(p).packageName;
        //        try {
        //            String pName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        //            ApplicationInfo a = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        //            item.setImage(ico = getPackageManager().getApplicationIcon(packages.getInstance(p).packageName));
        //        } catch (PackageManager.NameNotFoundException e) {
        //            e.printStackTrace();
        //        }


        //        item.setSize(packages.getInstance(position).dataDir);
        //        CPUCoolerFrag.apps.add(item);
        //        mDataSet.add(position, text);
        try {


            mAdapter.notifyItemInserted(position)
        } catch (e: Exception) {

        }

    }


    fun remove(position: Int) {
        //        mDataSet.add(position, text);
        mAdapter.notifyItemRemoved(position)
        try {
            CPUCoolerFrag.apps.removeAt(position)
        } catch (e: Exception) {

        }

    }

    fun killall() {
        val packages: List<ApplicationInfo>
        val pm: PackageManager
        pm = applicationContext.packageManager
        //getInstance a list of installed apps.
        packages = pm.getInstalledApplications(0)
        val mActivityManager = applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val myPackage = applicationContext.applicationContext.packageName
        for (packageInfo in packages) {
            if (packageInfo.flags and ApplicationInfo.FLAG_SYSTEM == 1 || packageInfo.packageName == myPackage) continue
            mActivityManager.killBackgroundProcesses(packageInfo.packageName)
        }
    }


    override fun onBackPressed() {
        //        super.onBackPressed();
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
            val handler6 = Handler()
            handler6.postDelayed({
                //                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5);

                finish()
            }, 1000)
        }
    }


}
