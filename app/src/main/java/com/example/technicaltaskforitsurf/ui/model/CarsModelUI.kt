package com.example.technicaltaskforitsurf.ui.model

import com.example.technicaltaskforitsurf.domain.model.CarsModelDomain

data class CarsModelUI(
    val id: Int? = null,
    val releaseYear: Int,
    val engineValue: Int,
    val dateJoined: Long,
    val imageUrl: String,
    val carName: String
)

fun CarsModelDomain.toUI() =
    CarsModelUI(id, releaseYear, engineValue, dateJoined, imageUrl, carName)

fun CarsModelUI.toDomain() =
    CarsModelDomain(id, releaseYear, engineValue, dateJoined, imageUrl, carName)
