package es.jac.gymlog.fragments.settings

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import es.jac.gymlog.R
import es.jac.gymlog.managers.AuthManager
import es.jac.gymlog.managers.FirestoreManager
import es.jac.gymlog.models.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        findPreference<Preference>("username")?.setOnPreferenceChangeListener { preference, newValue ->
            updateName(newValue.toString())
            true
        }
    }

    fun updateName(newName: String){
        lifecycleScope.launch (Dispatchers.IO){
            FirestoreManager().updateNombreChat(Usuario(AuthManager().getCurrentUser()?.email.toString(),newName))
            withContext(Dispatchers.Main){
                Toast.makeText(requireContext(),R.string.paramUpdated,Toast.LENGTH_SHORT).show()
            }
        }


    }


}