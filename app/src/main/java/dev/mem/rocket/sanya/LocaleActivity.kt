package dev.mem.rocket.sanya

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_loacale.*

import java.util.Locale

class LocaleActivity : AppCompatActivity() {

    lateinit var mAdapter: ArrayAdapter<String>
    private var myLocale: Locale? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loacale)

        mAdapter = ArrayAdapter(this@LocaleActivity, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.language_option))
        spLanguage.adapter = mAdapter

        if (LocaleHelper.getLanguage(this@LocaleActivity)!!.equals("en", ignoreCase = true)) {
            spLanguage.setSelection(mAdapter.getPosition("English"))
        } else if (LocaleHelper.getLanguage(this@LocaleActivity)!!.equals("ar", ignoreCase = true)) {
            spLanguage.setSelection(mAdapter.getPosition("Arabic"))
        } else if (LocaleHelper.getLanguage(this@LocaleActivity)!!.equals("fr", ignoreCase = true)) {
            spLanguage.setSelection(mAdapter.getPosition("French"))
        } else if (LocaleHelper.getLanguage(this@LocaleActivity)!!.equals("de", ignoreCase = true)) {
            spLanguage.setSelection(mAdapter.getPosition("German"))
        } else if (LocaleHelper.getLanguage(this@LocaleActivity)!!.equals("it", ignoreCase = true)) {
            spLanguage.setSelection(mAdapter.getPosition("Italian"))
        } else if (LocaleHelper.getLanguage(this@LocaleActivity)!!.equals("pt-rBR", ignoreCase = true)) {
            spLanguage.setSelection(mAdapter.getPosition("Portugal"))
        } else {
            spLanguage.setSelection(mAdapter.getPosition("Spanish"))
        }


        spLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                val context: Context
                val resources: Resources
                when (i) {
                    0 -> {
                        context = LocaleHelper.setLocale(this@LocaleActivity, "en")
                        resources = context.resources
                        textView.text = resources.getString(R.string.app_name)
                    }
                    1 -> {
                        context = LocaleHelper.setLocale(this@LocaleActivity, "ar")
                        resources = context.resources
                        textView.text = resources.getString(R.string.app_name)
                        setLocale("ar")
                    }
                    2 -> {
                        context = LocaleHelper.setLocale(this@LocaleActivity, "fr")
                        resources = context.resources
                        textView.text = resources.getString(R.string.app_name)
                    }
                    3 -> {
                        context = LocaleHelper.setLocale(this@LocaleActivity, "de")
                        resources = context.resources
                        textView.text = resources.getString(R.string.app_name)
                        startActivity(Intent(this@LocaleActivity, MainActivity::class.java))
                    }
                    4 -> {
                        context = LocaleHelper.setLocale(this@LocaleActivity, "it")
                        resources = context.resources
                        textView.text = resources.getString(R.string.app_name)
                    }
                    5 -> {
                        context = LocaleHelper.setLocale(this@LocaleActivity, "pt-rBR")
                        resources = context.resources
                        textView.text = resources.getString(R.string.app_name)
                    }
                    6 -> {
                        context = LocaleHelper.setLocale(this@LocaleActivity, "es-rES")
                        resources = context.resources
                        textView.text = resources.getString(R.string.app_name)
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    fun setLocale(lang: String) {
        myLocale = Locale(lang)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this@LocaleActivity, MainActivity::class.java)
        startActivity(refresh)
    }
}
