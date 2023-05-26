package com.example.technicaltaskforitsurf.domain.model

data class CarsModelDomain(
    val id: Int? = null,
    val releaseYear: Int,
    val engineValue: Int,
    val dateJoined: Long,
    val imageUrl: String,
    val carName: String
)