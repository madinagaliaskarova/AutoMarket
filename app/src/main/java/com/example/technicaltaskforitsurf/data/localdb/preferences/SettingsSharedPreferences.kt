package com.example.technicaltaskforitsurf.data.localdb.preferences

import android.content.SharedPreferences
import com.example.technicaltaskforitsurf.data.localdb.preferences.PreferencesKeys.isSubscribedKey
import com.example.technicaltaskforitsurf.data.localdb.preferences.PreferencesKeys.uploadedCarsKey
import com.example.technicaltaskforitsurf.data.localdb.preferences.PreferencesKeys.viewedCarsKey
import javax.inject.Inject

class SettingsSharedPreferences @Inject constructor(private val preferences: SharedPreferences) {

    var isSubscribed: Boolean
        get() = preferences.getBoolean(isSubscribedKey, false)
        set(value) = preferences.edit().putBoolean(isSubscribedKey, value).apply()

    var viewedCars: Int
        get() = preferences.getInt(viewedCarsKey, 0)
        set(value) = preferences.edit().putInt(viewedCarsKey, value).apply()

    var uploadedCars: Int
        get() = preferences.getInt(uploadedCarsKey, 0)
        set(value) = preferences.edit().putInt(uploadedCarsKey, value).apply()

    fun resetData(action :() -> Unit) {
        preferences.edit().clear().apply()
        action()
    }
}