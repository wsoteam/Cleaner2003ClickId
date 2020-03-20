package ochen.mem.rocket.sanyakekes.lang

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import ochen.mem.rocket.sanyakekes.R

class LanguagesDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.argb(200, 255, 255, 255)))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

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
        ) { dialog, which -> dialog.dismiss() }

        return dialogBuilder.create()
    }


}
