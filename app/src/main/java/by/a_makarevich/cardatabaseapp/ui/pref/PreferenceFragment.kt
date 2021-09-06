package by.a_makarevich.cardatabaseapp.ui.pref

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import by.a_makarevich.cardatabaseapp.R

class PreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }


}