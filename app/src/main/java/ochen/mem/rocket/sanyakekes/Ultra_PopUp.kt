package ochen.mem.rocket.sanyakekes

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import android.view.animation.OvershootInterpolator
import com.google.android.gms.ads.AdRequest

import ochen.mem.rocket.sanyakekes.AAA.Applying_Ultra
import ochen.mem.rocket.sanyakekes.OOP.PowersClass


import java.util.ArrayList

import ochen.mem.rocket.sanyakekes.PPP.PowerAdapter
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.banner_layout.adView
import kotlinx.android.synthetic.main.ultra_popup.*

/**
 * Created by intag pc on 2/19/2017.
 */

class Ultra_PopUp : Activity() {

    lateinit var mAdapter: PowerAdapter
    internal var items: MutableList<PowersClass> = mutableListOf()
    internal var hour: Int = 0
    internal var min: Int = 0
//    private var mAdView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = intent.extras
        setContentView(R.layout.ultra_popup)

        val adRequest = AdRequest.Builder().build()
        if(!SubscriptionProvider.hasSubscription()) {
            adView!!.loadAd(adRequest)
        }


        try {

            hour = Integer.parseInt(b!!.getString("hour")!!.replace("[^0-9]".toRegex(), "")) - Integer.parseInt(b!!.getString("hournormal")!!.replace("[^0-9]".toRegex(), ""))
            min = Integer.parseInt(b!!.getString("minutes")!!.replace("[^0-9]".toRegex(), "")) - Integer.parseInt(b!!.getString("minutesnormal")!!.replace("[^0-9]".toRegex(), ""))
        } catch (e: Exception) {
            hour = 4
            min = 7
        }

        if (hour == 0 && min == 0) {
            hour = 4
            min = 7
        }

        addedtime.text = "(+" + hour + " h " + Math.abs(min) + " m)"
        addedtimedetail.text = getString(R.string.extended_battery_up_to) + Math.abs(hour) + "h " + Math.abs(min) + "m"

        applied.setOnClickListener {
            val i = Intent(this@Ultra_PopUp, Applying_Ultra::class.java)
            startActivity(i)
            //                if (Build.VERSION.SDK_INT >= 21) {
            //                    getWindow().setNavigationBarColor(Color.parseColor("#000000"));
            //                    getWindow().setStatusBarColor(Color.parseColor("#000000"));
            //                }

            finish()
        }

        items = ArrayList()

        recycler_view.itemAnimator = SlideInLeftAnimator()
        //                RecyclerView recycler_view = (RecyclerView) findViewById(R.id.list);
        //                recycler_view.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

        recycler_view.itemAnimator!!.addDuration = 200

        mAdapter = PowerAdapter(items)
        val mLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recycler_view.layoutManager = mLayoutManager
        recycler_view.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recycler_view.computeHorizontalScrollExtent()
        recycler_view.adapter = mAdapter
        mAdapter.notifyDataSetChanged()

        /*

        final Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Block Acess to Memory and Battery Draning ApplicationsClass", 4);

            }
        }, 5000);

        final Handler handler6 = new Handler();
        handler6.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5);

            }
        }, 6000);\
        */

        //        final Handler handler6 = new Handler();
        //        handler4.postDelayed(new Runnable() {
        //            @Override
        //            public void run() {
        //                add("Use Black and White Scheme To Avoid Battery Draning", 3);
        //            }
        //        }, 4000);


    }

    fun add(text: String, position: Int) {
        val item = PowersClass(text)
        items.add(item)
        //        mDataSet.add(position, text);
        mAdapter.notifyItemInserted(position)

    }

    //    if(position==4)
    //    {
    //        mAdapter.notifyItemMoved(4,0);
    //    }
    //    else  if(position==5)
    //    {
    //        mAdapter.notifyItemMoved(5,0);
    //    }

    override fun onBackPressed() {
        // super.onBackPressed();
    }

}
