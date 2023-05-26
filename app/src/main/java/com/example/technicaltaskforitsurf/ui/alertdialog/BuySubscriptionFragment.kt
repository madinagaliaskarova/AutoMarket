package com.example.technicaltaskforitsurf.ui.alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class BuySubscriptionFragment : DialogFragment() {

    val price = "9.99 USD"
    val period = "1 month"

    val alertDialog = AlertDialog.Builder(requireContext())
        .setTitle("Подписка")
        .setMessage("Цена: $price\nПериод: $period")
        .setPositiveButton("Купить") { dialog, which ->
        }
        .setNegativeButton("Нет, спасибо") { dialog, which ->
        }
        .create()
}