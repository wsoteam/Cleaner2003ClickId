package ochen.mem.rocket.sanyakekes.PPP

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import android.view.animation.OvershootInterpolator
import com.google.android.gms.ads.AdRequest

import ochen.mem.rocket.sanyakekes.OOP.PowersClass


import java.util.ArrayList

import ochen.mem.rocket.sanyakekes.R
import ochen.mem.rocket.sanyakekes.SubscriptionProvider
import ochen.mem.rocket.sanyakekes.utils.PreferencesProvider
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.banner_layout.adView
import kotlinx.android.synthetic.main.powersaving_popup.*

/**
 * Created by intag pc on 2/19/2017.
 */

class PowerSaving_popup : Activity() {
    internal var items: MutableList<PowersClass> = mutableListOf()

    lateinit var mAdapter: PowerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val b = intent.extras
        setContentView(R.layout.powersaving_popup)

        val adRequest = AdRequest.Builder().build()
        if(!SubscriptionProvider.hasSubscription()) {
            adView!!.loadAd(adRequest)
        }


        items = ArrayList()
        applied.setOnClickListener {
            PreferencesProvider.getInstance().edit()
                    .putString("mode", "1")
                    .apply()

            val i = Intent(applicationContext, PowerSaving_Complition::class.java)
            startActivity(i)

            finish()
        }

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


    }

    fun add(text: String, position: Int) {


        val item = PowersClass(text)
        items.add(item)
        //        mDataSet.add(position, text);
        mAdapter.notifyItemInserted(position)
    }

    //
    override fun onBackPressed() {
        //   super.onBackPressed();
    }

}
