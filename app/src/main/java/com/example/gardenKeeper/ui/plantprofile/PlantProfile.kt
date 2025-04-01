package com.example.gardenKeeper.ui.plantprofile

import android.net.Uri

data class PlantProfile(
    val imageUri: Uri?,
    val type: String,
    val location: String,
    val needs: String,
    val obs: String
)
