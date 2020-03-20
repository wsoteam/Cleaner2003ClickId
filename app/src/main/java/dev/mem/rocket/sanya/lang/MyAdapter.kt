package dev.mem.rocket.sanya.lang

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import java.util.HashMap
import java.util.Locale

import dev.mem.rocket.sanya.MainActivity
import dev.mem.rocket.sanya.R
import kotlinx.android.synthetic.main.languages.view.*

class MyAdapter(private val context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private val imgs: TypedArray
    private val activity: Activity
    internal val localeMap: MutableMap<String, String>
    internal val values: Array<String> = arrayOf()

    init {
        imgs = context.resources.obtainTypedArray(R.array.flags)
        activity = context as Activity
        localeMap = HashMap()
        val availableLocales = context.getResources().getStringArray(
                R.array.languages)

        for (i in availableLocales.indices) {
            var localString = availableLocales[i]
            if (localString.contains("-")) {
                localString = localString.substring(0,
                        localString.indexOf("-"))
            }
            val locale = Locale(localString)
            values[i] = (locale.displayLanguage + " ("
                    + availableLocales[i] + ")")
            localeMap[values[i]] = availableLocales[i]
        }
        //values[0] = context.getString(R.string.device) + " ("
        //        + Locale.getDefault().getLanguage() + ")";
        //localeMap.put(values[0], Locale.getDefault().getLanguage());
        //
        // Arrays.sort(values, 0, values.length);

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.languages, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mImgView.setImageResource(imgs.getResourceId(position, -1))
    }

    override fun getItemCount(): Int {
        return imgs.length()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var mImgView: ImageView

        init {
            itemView.item.setOnClickListener {
                val res = context.resources
                val dm = res.displayMetrics
                val conf = res.configuration
                val localString = localeMap[values[adapterPosition]]
                if (localString!!.contains("-")) {
                    conf.locale = Locale(localString.substring(0,
                            localString.indexOf("-")), localString.substring(
                            localString.indexOf("-") + 1, localString.length))
                } else {
                    conf.locale = Locale(localString)
                }
                //PreferenceManager.getDefaultSharedPreferences(context).edit().putString("MYLABEL", "myStringToSave").apply();
                val settings = PreferenceManager
                        .getDefaultSharedPreferences(context)
                res.updateConfiguration(conf, dm)
                settings.edit().putString(SettingsFragment.LANGUAGE_SETTING, localString).apply()

                // Refresh the app
                val refresh = Intent(context, MainActivity::class.java)
                activity.startActivity(refresh)
                activity.setResult(SettingsFragment.LANGUAGE_CHANGED)
                activity.finish()
            }
            mImgView = itemView.findViewById(R.id.mImgView)
        }
    }
}
