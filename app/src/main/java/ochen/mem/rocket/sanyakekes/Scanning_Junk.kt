package ochen.mem.rocket.sanyakekes

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation

import ochen.mem.rocket.sanyakekes.BCJ.Junk_Apps_Adapter
import ochen.mem.rocket.sanyakekes.OOP.ApplicationsClass

import com.github.ybq.android.spinkit.style.ThreeBounce
import com.google.android.gms.ads.AdRequest
import com.skyfishjy.library.RippleBackground

import java.util.ArrayList
import java.util.Timer
import java.util.TimerTask

import ochen.mem.rocket.sanyakekes.service.CleanService

import ochen.mem.rocket.sanyakekes.Constants.adsShow
import kotlinx.android.synthetic.main.banner_layout.adView
import kotlinx.android.synthetic.main.scanning_junk.*

class Scanning_Junk : Activity(), AdMobFullscreenManager.AdMobFullscreenDelegate {

    internal var check = 0
    internal var packages: List<ApplicationInfo> = listOf()
    internal var prog = 0
    lateinit var T2: Timer
    internal var mAdapter: Junk_Apps_Adapter? = null
    var apps: MutableList<ApplicationsClass> = mutableListOf()

    private var fullscreenManager: AdMobFullscreenManager? = null
//    private var mAdView: AdView? = null


    private var handler: Handler? = null
    private var junk: Bundle? = null

    private val adManager: AdMobFullscreenManager?
        get() {
            if (fullscreenManager == null) {
                configureManager()
            }
            return fullscreenManager
        }

    private fun startFinishAnim() {
        scan1.hide()
        scan2.hide()
        scan3.hide()
        scan4.hide()
        scan5.hide()
        scan6.hide()
        val rippleBackground = findViewById<View>(R.id.content) as RippleBackground
        rippleBackground.startRippleAnimation()
        //                front.setImageResource(0);
        //                imageView.setImageResource(0);
        //                front.setImageDrawable(ContextCompat.getDrawable(Scanning_Junk.this, R.drawable.task_complete));
        //                imageView.setImageDrawable(ContextCompat.getDrawable(Scanning_Junk.this, R.drawable.green_circle));
        front.setImageResource(R.drawable.task_complete)
        back.setImageResource(R.drawable.green_circle)

        val doubleBounce = ThreeBounce()
        spin_kit.setIndeterminateDrawable(doubleBounce)
        spin_kit.visibility = View.GONE

        scanning.setPadding(20, 0, 0, 0)


        if (Build.VERSION.SDK_INT < 23) {

            scanning.setTextAppearance(applicationContext, android.R.style.TextAppearance_Medium)
            scanning.text = junk!!.getString("junk")!! + getString(R.string.MB_of_junk_files_are_cleared)

        } else {

            scanning.setTextAppearance(android.R.style.TextAppearance_Medium)
            scanning.text = junk!!.getString("junk")!! + getString(R.string.MB_of_junk_files_are_cleared)
        }


        val anim = AnimatorInflater.loadAnimator(applicationContext, R.animator.flipping) as ObjectAnimator
        anim.target = front
        anim.duration = 3000
        anim.start()

        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

                scanning.text = getString(R.string.cleared) + junk!!.getString("junk") + getString(R.string.MB)
                scanning.setTextColor(Color.parseColor("#FFFFFF"))
            }

            override fun onAnimationEnd(animation: Animator) {
                rippleBackground.stopRippleAnimation()
                if (adsShow) {

                    adManager!!.completed()
                } else {
                    val handler7 = Handler()
                    handler7.postDelayed({
                        //                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
                        //                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
                        val intent = Intent(this@Scanning_Junk, MainActivity::class.java)
                        intent.putExtra("frag", 3)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }, 1000)

                }

            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

        files.text = ""
    }

    private fun startMainAnimation() {
        val adRequest = AdRequest.Builder().build()
        if(!SubscriptionProvider.hasSubscription()) {
            adView!!.loadAd(adRequest)
        }
        apps = ArrayList()

        val rotate = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 1500
        rotate.repeatCount = 4
        rotate.interpolator = LinearInterpolator()

        rotate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                T2.cancel()
                T2.purge()
                scan1.hide()
                scan2.hide()
                scan3.hide()
                scan4.hide()
                scan5.hide()
                scan6.hide()
                handler!!.sendEmptyMessage(END_ANIMATION)
            }

            override fun onAnimationRepeat(animation: Animation) {
                check++
                startAnim(check)
            }
        })
        front.startAnimation(rotate)

        packages = packageManager.getInstalledApplications(0)
        val mActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        T2 = Timer()
        T2.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (prog < packages.size) {
                        files.text = "" + packages[prog].sourceDir
                        prog++
                    } else {
                        T2.cancel()
                        T2.purge()
                    }
                }
            }
        }, 80, 80)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        junk = intent.extras
        setContentView(R.layout.scanning_junk)
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                when (msg.what) {
                    END_ANIMATION ->
                        //TODO: if android <= Nougat startService
                        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
                            val i = Intent(this@Scanning_Junk, CleanService::class.java)
                            i.putExtra("junk", junk!!.getString("junk"))
                            this@Scanning_Junk.startService(i)
                        } else {
                            handler!!.sendEmptyMessage(END_CLEAN)
                        }
                    END_CLEAN -> startFinishAnim()
                }
            }
        }
        if (intent.getBooleanExtra("isScanned", false)) {
            handler!!.sendEmptyMessage(END_CLEAN)
        } else {
            startMainAnimation()
        }
        /*recycler_view = (RecyclerView) findViewById(R.id.recycler_view);

        recycler_view.setItemAnimator(new SlideInLeftAnimator());
        recycler_view.addItemDecoration(new SimpleDividerItemDecoration(this));
        mAdapter = new Junk_Apps_Adapter(apps);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recycler_view.computeHorizontalScrollExtent();
        recycler_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        recycler_view.addItemDecoration(new SimpleDividerItemDecoration(this));*/
    }


    internal fun startAnim(i1: Int) {
        if (i1 == 1) {
            scan1.show()
            scan3.show()
            scan5.show()

            scan2.hide()
            scan4.hide()
            scan6.hide()
        } else if (i1 == 2) {
            scan2.show()
            scan4.show()
            scan6.show()

            scan1.hide()
            scan3.hide()
            scan5.hide()
        } else if (i1 == 3) {
            scan2.show()
            scan4.show()
            scan6.show()

            scan1.show()
            scan3.show()
            scan5.show()
        } else if (i1 == 4) {
            scan2.show()
            scan3.show()
            scan5.show()

            scan1.show()
            scan2.show()
            scan6.show()
        }


        // or avi.smoothToShow();
    }

    internal fun stopAnim() {
        scan1.hide()
        scan3.hide()
        scan5.hide()

        scan2.show()
        scan4.show()
        scan6.show()
        // or avi.smoothToHide();
    }


    fun add(text: String, position: Int) {


        val p = 0 + (Math.random() * (packages.size - 1 - 0 + 1)).toInt()


        var ico: Drawable? = null


        val packageName = packages[p].packageName
        var item = try {
            val pName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)) as String
            val a = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            ico = packageManager.getApplicationIcon(packages[p].packageName)
            ApplicationsClass(packages[p].dataDir, ico)

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ApplicationsClass(packages[p].dataDir, null)
        }

        apps.add(item)
        //        mDataSet.add(position, text);
        mAdapter!!.notifyItemInserted(position)
    }


    fun remove(position: Int) {
        //        mDataSet.add(position, text);
        mAdapter!!.notifyItemRemoved(position)
        apps.removeAt(position)
    }


    inner class SimpleDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
        private var mDivider: Drawable? = null

        init {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mDivider = context.resources.getDrawable(R.drawable.line_divvide, context.theme)
            } else {
                mDivider = context.resources.getDrawable(R.drawable.line_divvide)

            }
        }

        override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight

            val childCount = parent.childCount
            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                val top = child.bottom + params.bottomMargin
                val bottom = top + mDivider!!.intrinsicHeight

                mDivider!!.setBounds(left, top, right, bottom)
                mDivider!!.draw(c)
            }
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

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        junk = savedInstanceState
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("junk", junk!!.getString("junk"))
        super.onSaveInstanceState(outState)
    }

    override fun ADLoaded() {
        if (adManager!!.tryingShowDone) {
            adManager!!.showAdd()
        }
    }

    override fun ADIsClosed() {
        if (adManager!!.tryingShowDone) {

            val handler7 = Handler()
            handler7.postDelayed({
                //                  add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
                //                  add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
                val intent = Intent(this@Scanning_Junk, MainActivity::class.java)
                intent.putExtra("frag", 3)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }, 1000)

        }
    }

    companion object {

        private val END_ANIMATION = 1
        private val END_CLEAN = 3
    }
}
