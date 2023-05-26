package com.example.technicaltaskforitsurf.ui.alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.technicaltaskforitsurf.data.localdb.preferences.SettingsSharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlertDialogFragment : DialogFragment() {

    @Inject
    lateinit var preferences: SettingsSharedPreferences

    companion object {
        fun newInstance(title: String, message: String): AlertDialogFragment {
            val fragment = AlertDialogFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putString("message", message)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString("title")
        val message = arguments?.getString("message")

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Купить") { dialog, which ->
                preferences.isSubscribed = true
            }
            .setNegativeButton("Закрыть") { dialog, which ->
                dialog.dismiss()
            }

        return builder.create()
    }
}