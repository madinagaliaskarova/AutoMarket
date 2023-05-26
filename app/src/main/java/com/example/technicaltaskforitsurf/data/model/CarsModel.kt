package com.example.technicaltaskforitsurf.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.technicaltaskforitsurf.domain.model.CarsModelDomain

@Entity(tableName = "cars")
data class CarsModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val releaseYear: Int,
    val engineValue: Int,
    val dateJoined: Long,
    val imageUrl: String,
    val carName: String
)

fun CarsModelDomain.toData() =
    CarsModel(id, releaseYear, engineValue, dateJoined, imageUrl, carName)

fun CarsModel.toDomain() =
    CarsModelDomain(id, releaseYear, engineValue, dateJoined, imageUrl, carName)