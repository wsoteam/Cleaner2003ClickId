package ochen.mem.rocket.sanyakekes.AAA

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast

import ochen.mem.rocket.sanyakekes.R

/**
 * Created by intag pc on 3/3/2017.
 */

class AppListener : BroadcastReceiver() {


    /// Listen for newly installed app and protect it

    override fun onReceive(context: Context, arg1: Intent) {
        // TODO Auto-generated method stub

        val data = arg1.data
        val installedPackageName = data!!.encodedSchemeSpecificPart



        if (installedPackageName != "fast.cleaner.battery.saver") {
            //            Toast.makeText(context, installedPackageName + "", Toast.LENGTH_SHORT).show();

            val packageManager = context.applicationContext.packageManager
            try {
                val appName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(installedPackageName, PackageManager.GET_META_DATA)) as String
                val inflater = LayoutInflater.from(context)
                val layout = inflater.inflate(R.layout.my_toast, null)

                val text = layout.findViewById<View>(R.id.textView1) as TextView
                text.text = appName + context.getString(R.string.is_optimised_by)

                val toast = Toast(context)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 120)
                toast.duration = Toast.LENGTH_LONG
                toast.view = layout
                toast.show()
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

        }
    }
}
