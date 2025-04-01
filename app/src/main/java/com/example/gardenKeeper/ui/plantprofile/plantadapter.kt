package com.example.gardenKeeper.ui.plantprofile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenkeeper.R

class PlantAdapter(private val plantProfiles: MutableList<PlantProfile>) : RecyclerView.Adapter<PlantAdapter.PlantViewHolder>() {

    class PlantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val plantImage: ImageView = view.findViewById(R.id.plant_image)
        val plantType: TextView = view.findViewById(R.id.plant_type)
        val plantLocation: TextView = view.findViewById(R.id.plant_location)
        val plantNeeds: TextView = view.findViewById(R.id.plant_needs)
        val plantObserv: TextView = view.findViewById(R.id.plant_obs)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant_profile, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plantProfile = plantProfiles[position]

        if (plantProfile.imageUri != null){
            holder.plantImage.setImageURI(plantProfile.imageUri)
        }else {
            holder.plantImage.setImageResource(R.drawable.plant_image_placeholder)
        }

        holder.plantType.text = plantProfile.type
        holder.plantLocation.text = plantProfile.location
        holder.plantNeeds.text = plantProfile.needs
        holder.plantObserv.text = plantProfile.obs
    }

    override fun getItemCount(): Int {
        return plantProfiles.size
    }

    fun updatePlantProfiles(newPlantProfiles: MutableList<PlantProfile>) {
        plantProfiles.clear() // Limpiar la lista existente
        plantProfiles.addAll(newPlantProfiles) // Agregar los nuevos perfiles
        notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
    }

}
