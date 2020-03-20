package ochen.mem.rocket.sanyakekes.BCJ

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Toast

import ochen.mem.rocket.sanyakekes.Noraml_Mode
import ochen.mem.rocket.sanyakekes.PPP.Pick_Apps
import ochen.mem.rocket.sanyakekes.R

import java.util.Locale

import android.content.ContentValues.TAG
import ochen.mem.rocket.sanyakekes.utils.PreferencesProvider
import kotlinx.android.synthetic.main.powersaving_main.*

/**
 * Created by intag pc on 2/21/2017.
 */

class BatterySaver_Black : Activity() {


    ///Apply Extrem Power Saving Mode by allowing very few Applications to run

    internal var check = 0


    private val mBatInfoReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            //
            //            waveView.setProgressValue(level);
            ////            waveView.setBottomTitle(level+"%");
            //            waveView.setCenterTitle(level+"%");

            batteryremaning.text = getString(R.string.battery_remaining) + level + "%"

            if (level <= 5) {

                //                houru.setText(3+"");
                //                minutesu.setText(55+"");

                timeremaning.setText(R.string.h_remaining)


            }
            if (level > 5 && level <= 10) {

                //                houru.setText(6+"");
                //                minutesu.setText(0+"");

                timeremaning.setText(R.string.hh_remaining)


            }
            if (level > 10 && level <= 15) {


                //                houru.setText(8+"");
                //                minutesu.setText(25+"");

                timeremaning.setText(R.string.hhh_remaining)


            }
            if (level > 15 && level <= 25) {


                //                houru.setText(12+"");
                //                minutesu.setText(55+"");

                timeremaning.setText(R.string.hhhh_remaining)

            }
            if (level > 25 && level <= 35) {


                //                houru.setText(19+"");
                //                minutesu.setText(2+"");

                timeremaning.setText(R.string.hhhhh_remaining)


            }
            if (level > 35 && level <= 50) {

                //
                //                houru.setText(22+"");
                //                minutesu.setText(0+"");

                timeremaning.setText(R.string.hhhhhh_remaining)


            }
            if (level > 50 && level <= 65) {

                //
                //                houru.setText(28+"");
                //                minutesu.setText(15+"");

                timeremaning.setText(R.string.hhhhhhh_remaining)


            }
            if (level > 65 && level <= 75) {


                //                houru.setText(30+"");
                //                minutesu.setText(55+"");

                timeremaning.setText(R.string.hhhhhhhh_remaining)


            }
            if (level > 75 && level <= 85) {

                //
                //                houru.setText(38+"");
                //                minutesu.setText(5+"");

                timeremaning.setText(R.string.hhhhhhhhh_remaining)


            }
            if (level > 85 && level <= 100) {


                //                houru.setText(60+"");
                //                minutesu.setText(55+"");

                timeremaning.setText(R.string.hhhhhhhhhh_remaining)


            }

        }
    }
//    private var mAdView: AdView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 17) {
            setContentView(R.layout.powersaving_maintextclock)
        } else {
            setContentView(R.layout.powersaving_main)

//            mAdView = findViewById(R.id.adView)
//            val adRequest = AdRequest.Builder().build()
//            mAdView!!.loadAd(adRequest)
        }

        changepic()
        registerReceiver(this.mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))


        dots.setOnClickListener {
            if (check % 2 == 0) {
                disable.visibility = View.VISIBLE
                disable.setOnClickListener {
                    val i = Intent(this@BatterySaver_Black, Noraml_Mode::class.java)
                    startActivity(i)

                    finish()
                }
                check++

            } else {
                disable.visibility = View.INVISIBLE
                check++
            }
        }


        alaram.setOnClickListener {
            PreferencesProvider.getInstance().edit().putString("button", "1")
                    .commit()

            if (PreferencesProvider.getInstance().getString("button1", "0") == "0") {
                val i = Intent(this@BatterySaver_Black, Pick_Apps::class.java)
                startActivity(i)
            } else if (PreferencesProvider.getInstance().getString("button1", "0") == "1") {
                playstore()
            } else if (PreferencesProvider.getInstance().getString("button1", "0") == "2") {
                calculator()
            } else if (PreferencesProvider.getInstance().getString("button1", "0") == "3") {
                alaram()
            } else if (PreferencesProvider.getInstance().getString("button1", "0") == "4") {
                contacts()
            } else if (PreferencesProvider.getInstance().getString("button1", "0") == "5") {
                map()
            } else if (PreferencesProvider.getInstance().getString("button1", "0") == "6") {
                camera()
            }
        }

        calculator.setOnClickListener {
            PreferencesProvider.getInstance().edit()
                    .putString("button", "2")
                    .apply()

            if (PreferencesProvider.getInstance().getString("button2", "0") == "0") {
                val i = Intent(this@BatterySaver_Black, Pick_Apps::class.java)
                startActivity(i)
            } else if (PreferencesProvider.getInstance().getString("button2", "0") == "1") {
                playstore()
            } else if (PreferencesProvider.getInstance().getString("button2", "0") == "2") {
                calculator()
            } else if (PreferencesProvider.getInstance().getString("button2", "0") == "3") {
                alaram()
            } else if (PreferencesProvider.getInstance().getString("button2", "0") == "4") {
                contacts()
            } else if (PreferencesProvider.getInstance().getString("button2", "0") == "5") {
                map()
            } else if (PreferencesProvider.getInstance().getString("button2", "0") == "6") {
                camera()
            }
        }

        phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            startActivity(intent)
        }

        internet.setOnClickListener {
            val urlString = "http://www.google.com"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.android.chrome")
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null)
                startActivity(intent)
            }
        }

        settings.setOnClickListener { startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0) }
        messages.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.type = "vnd.android-dir/mms-sms"
            startActivity(intent)
        }

        playstore.setOnClickListener {
            PreferencesProvider.getInstance().edit()
                    .putString("button", "3")
                    .apply()

            if (PreferencesProvider.getInstance().getString("button3", "0") == "0") {
                val i = Intent(this@BatterySaver_Black, Pick_Apps::class.java)
                startActivity(i)
            } else if (PreferencesProvider.getInstance().getString("button3", "0") == "1") {
                playstore()
            } else if (PreferencesProvider.getInstance().getString("button3", "0") == "2") {
                calculator()
            } else if (PreferencesProvider.getInstance().getString("button3", "0") == "3") {
                alaram()
            } else if (PreferencesProvider.getInstance().getString("button3", "0") == "4") {
                contacts()
            } else if (PreferencesProvider.getInstance().getString("button3", "0") == "5") {
                map()
            } else if (PreferencesProvider.getInstance().getString("button3", "0") == "6") {
                camera()
            }
        }

        contacts.setOnClickListener {
            PreferencesProvider.getInstance().edit()
                    .putString("button", "4")
                    .apply()

            if (PreferencesProvider.getInstance().getString("button4", "0") == "0") {
                val i = Intent(this@BatterySaver_Black, Pick_Apps::class.java)
                startActivity(i)
            } else if (PreferencesProvider.getInstance().getString("button4", "0") == "1") {
                playstore()
            } else if (PreferencesProvider.getInstance().getString("button4", "0") == "2") {
                calculator()
            } else if (PreferencesProvider.getInstance().getString("button4", "0") == "3") {
                alaram()
            } else if (PreferencesProvider.getInstance().getString("button4", "0") == "4") {
                contacts()
            } else if (PreferencesProvider.getInstance().getString("button4", "0") == "5") {
                map()
            } else if (PreferencesProvider.getInstance().getString("button4", "0") == "6") {
                camera()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  intent: Intent?) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val uri = intent?.data
                val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)

                val cursor = contentResolver.query(uri!!, projection, null, null, null)
                cursor!!.moveToFirst()

                val numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val number = cursor.getString(numberColumnIndex)

                val nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val name = cursor.getString(nameColumnIndex)

                Log.d(TAG, "ZZZ number : $number , name : $name")

                val k = Intent(Intent.ACTION_DIAL)
                k.data = Uri.parse("tel:$number")
                startActivity(k)

            }
        }
    }
    //    @Override
    //    public void onBackPressed() {
    ////        super.onBackPressed();
    //    }


    fun check() {


    }

    fun playstore() {
        //////play store///////////////////////////////////////
        val appPackageName = packageName // getPackageName() from Context or Activity object
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://")))
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/")))
        }

        ////////////////////////////////////////////////////////
    }

    fun contacts() {

        //////////////////contacts//////////////////////////////
        val uri = Uri.parse("content://contacts")
        val intent = Intent(Intent.ACTION_PICK, uri)
        intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        startActivityForResult(intent, 1)
        ////////////////////////////////////////////////////////////
    }

    fun calculator() {
        ///////////////////////////calculator////////////////////////
        var intent: Intent


        try {

            intent = Intent()
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.component = ComponentName("com.sec.android.app.popupcalculator", "com.sec.android.app.popupcalculator.Calculator")
            startActivity(intent)
            //
        } catch (e: Exception) {
            intent = Intent()
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.component = ComponentName("com.android.calculator2", "com.android.calculator2.Calculator")
            startActivity(intent)

        }

        /////////////////////////////////////////////////////////////////////////////////////////////
    }

    fun alaram() {
        val i = Intent(AlarmClock.ACTION_SET_ALARM)
        startActivity(i)
    }

    fun map() {
        val uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?&daddr=%f,%f (%s)", 12f, 2f, "")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            try {
                val unrestrictedIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(unrestrictedIntent)
            } catch (innerEx: ActivityNotFoundException) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show()
            }

        }

    }

    fun camera() {

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
            }
        } else {
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            startActivityForResult(intent, 0)
        }


    }

    fun changepic() {
        if (PreferencesProvider.getInstance().getString("button1", "0") == "0") {
            alaram.setImageResource(R.drawable.add)
        } else if (PreferencesProvider.getInstance().getString("button1", "0") == "1") {
            alaram.setImageResource(R.drawable.gp)
        } else if (PreferencesProvider.getInstance().getString("button1", "0") == "2") {
            alaram.setImageResource(R.drawable.calc)

        } else if (PreferencesProvider.getInstance().getString("button1", "0") == "3") {
            alaram.setImageResource(R.drawable.clock)

        } else if (PreferencesProvider.getInstance().getString("button1", "0") == "4") {
            alaram.setImageResource(R.drawable.contacts)

        } else if (PreferencesProvider.getInstance().getString("button1", "0") == "5") {
            alaram.setImageResource(R.drawable.map)

        } else if (PreferencesProvider.getInstance().getString("button1", "0") == "6") {
            alaram.setImageResource(R.drawable.camera)
        }


        if (PreferencesProvider.getInstance().getString("button2", "0") == "0") {
            calculator.setImageResource(R.drawable.add)
        } else if (PreferencesProvider.getInstance().getString("button2", "0") == "1") {
            calculator.setImageResource(R.drawable.gp)
        } else if (PreferencesProvider.getInstance().getString("button2", "0") == "2") {
            calculator.setImageResource(R.drawable.calc)

        } else if (PreferencesProvider.getInstance().getString("button2", "0") == "3") {
            calculator.setImageResource(R.drawable.clock)

        } else if (PreferencesProvider.getInstance().getString("button2", "0") == "4") {
            calculator.setImageResource(R.drawable.contacts)

        } else if (PreferencesProvider.getInstance().getString("button2", "0") == "5") {
            calculator.setImageResource(R.drawable.map)

        } else if (PreferencesProvider.getInstance().getString("button2", "0") == "6") {
            calculator.setImageResource(R.drawable.camera)
        }



        if (PreferencesProvider.getInstance().getString("button3", "0") == "0") {
            playstore.setImageResource(R.drawable.add)
        } else if (PreferencesProvider.getInstance().getString("button3", "0") == "1") {
            playstore.setImageResource(R.drawable.gp)
        } else if (PreferencesProvider.getInstance().getString("button3", "0") == "2") {
            playstore.setImageResource(R.drawable.calc)

        } else if (PreferencesProvider.getInstance().getString("button3", "0") == "3") {
            playstore.setImageResource(R.drawable.clock)

        } else if (PreferencesProvider.getInstance().getString("button3", "0") == "4") {
            playstore.setImageResource(R.drawable.contacts)

        } else if (PreferencesProvider.getInstance().getString("button3", "0") == "5") {
            playstore.setImageResource(R.drawable.map)

        } else if (PreferencesProvider.getInstance().getString("button3", "0") == "6") {
            playstore.setImageResource(R.drawable.camera)
        }



        if (PreferencesProvider.getInstance().getString("button4", "0") == "0") {
            contacts.setImageResource(R.drawable.add)
        } else if (PreferencesProvider.getInstance().getString("button4", "0") == "1") {
            contacts.setImageResource(R.drawable.gp)
        } else if (PreferencesProvider.getInstance().getString("button4", "0") == "2") {
            contacts.setImageResource(R.drawable.calc)

        } else if (PreferencesProvider.getInstance().getString("button4", "0") == "3") {
            contacts.setImageResource(R.drawable.clock)

        } else if (PreferencesProvider.getInstance().getString("button4", "0") == "4") {
            contacts.setImageResource(R.drawable.contacts)

        } else if (PreferencesProvider.getInstance().getString("button4", "0") == "5") {
            contacts.setImageResource(R.drawable.map)

        } else if (PreferencesProvider.getInstance().getString("button4", "0") == "6") {
            contacts.setImageResource(R.drawable.camera)
        }
    }


    override fun onResume() {
        super.onResume()
        changepic()
    }

    override fun onBackPressed() {
        //        super.onBackPressed();
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                startActivityForResult(intent, 0)
            } else {
                Toast.makeText(this, "Allow Permission To Use Camera App.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBatInfoReceiver)
    }
}
