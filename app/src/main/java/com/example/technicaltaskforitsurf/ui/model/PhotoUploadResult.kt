package com.example.technicaltaskforitsurf.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoUploadResult(
    var localPath: String,
    var imageUri: String? = null,
    var isChecked: Boolean = false,
    var orderNumber: Int = 0,
    var id: Int? = 0
): Parcelable