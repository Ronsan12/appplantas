package com.example.gardenKeeper.ui.plantprofile

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gardenkeeper.R

class PlantProfileViewModel(application: Application) : AndroidViewModel(application) {

    // Lista Mutable de los perfiles de las plantas
    private val _plantProfiles = MutableLiveData<MutableList<PlantProfile>>(
        mutableListOf())

    // LiveData para observar cambios en la lista
    val plantProfiles: LiveData<MutableList<PlantProfile>> = _plantProfiles

    // Uri de la imagen de la planta seleccionada
    private val _plantImageUri = MutableLiveData<Uri?>()
    val plantImageUri: LiveData<Uri?> = _plantImageUri


    // Inicialización de algunos perfiles como Ficus y Kenny
    init {
        _plantProfiles.value = mutableListOf(
            PlantProfile(getUriForResource(R.drawable.kenny), "Kenny (Palmera Kentia)", "Sala Principal", "Agua de 2 a 3 veces por semana", "Siempre revise el sustrato entre riegos, si está seco a unos 4 o 5 centímetros de profundidad, será el tiempo de regularla."),
            PlantProfile(getUriForResource(R.drawable.ficus), "Ficus", "Sala ventana a la calle", "Riego moderado", "Coloca el Ficus en lugares iluminados pero sin luz solar directa, ya que puede dañar sus hojas")
        )
    }
    private fun getUriForResource(resId: Int): Uri {
        return Uri.parse("android.resource://${getApplication<Application>().packageName}/$resId")
    }
    // Función para agregar un nuevo perfil

    fun addPlantProfile(plantProfile: PlantProfile) {
        val currentProfiles = _plantProfiles.value ?: mutableListOf()
        currentProfiles.add(plantProfile)
        _plantProfiles.value = currentProfiles
    }

    fun setPlantImageUri(uri: Uri) {
        _plantImageUri.value = uri
    }
}