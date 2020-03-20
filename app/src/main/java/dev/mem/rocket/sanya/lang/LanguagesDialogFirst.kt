package dev.mem.rocket.sanya.lang

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

import dev.mem.rocket.sanya.MainActivity
import dev.mem.rocket.sanya.R

class LanguagesDialogFirst : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.argb(200, 255, 255, 255)))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val settings = PreferenceManager
                .getDefaultSharedPreferences(activity)

        val dialogBuilder = AlertDialog.Builder(
                activity)
        //dialogBuilder.setTitle(getString(R.string.language));
        val listView = RecyclerView(activity)
        listView.layoutManager = GridLayoutManager(activity, 2)
        listView.adapter = MyAdapter(activity)

        dialogBuilder.setView(listView)
        dialogBuilder.setNegativeButton(R.string.cancel
        ) { dialog, which ->
            dialog.dismiss()
            PreferenceManager.getDefaultSharedPreferences(activity).edit().putString("MYLABEL", "myStringToSave").apply()
            val refresh = Intent(activity, MainActivity::class.java)
            activity.startActivity(refresh)
            activity.setResult(SettingsFragment.LANGUAGE_CHANGED)
            activity.finish()
        }

        return dialogBuilder.create()


    }

    fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_OUTSIDE) {
            println("TOuch outside the dialog ******************** ")
            this.dismiss()
        }
        return false
    }

}
