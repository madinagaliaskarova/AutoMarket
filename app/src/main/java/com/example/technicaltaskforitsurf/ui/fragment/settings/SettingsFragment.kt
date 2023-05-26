package com.example.technicaltaskforitsurf.ui.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notesm.databinding.FragmentSettingsBinding
import com.example.technicaltaskforitsurf.data.localdb.preferences.SettingsSharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var preferences: SettingsSharedPreferences

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        resetSettingsToDefault()
    }

    private fun resetSettingsToDefault() {
        binding.btnReset.setOnClickListener {
            preferences.resetData {
                requireActivity().recreate()
            }
        }
    }
}