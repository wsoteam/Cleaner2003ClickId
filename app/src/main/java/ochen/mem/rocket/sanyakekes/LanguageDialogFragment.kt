package ochen.mem.rocket.sanyakekes

import androidx.fragment.app.DialogFragment

class LanguageDialogFragment : DialogFragment() {

    internal val itemsLang = arrayOf("English", "Russian", "Ukraine")
    internal val items = arrayOf("en", "ru", "uk")
    internal var lang: String? = null
    internal var activity = MainActivity()

}