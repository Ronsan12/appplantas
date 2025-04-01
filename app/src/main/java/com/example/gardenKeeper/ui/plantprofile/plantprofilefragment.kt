package com.example.gardenKeeper.ui.plantprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenkeeper.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PlantProfileFragment : Fragment() {

    private lateinit var plantProfileViewModel: PlantProfileViewModel
    private lateinit var plantAdapter: PlantAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_plant_profiles, container, false)

        // Inicializar ViewModel
        plantProfileViewModel = ViewModelProvider(requireActivity())[PlantProfileViewModel::class.java]
        plantAdapter = PlantAdapter(mutableListOf()) // Inicializar con lista vacía

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_plant_profiles)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = plantAdapter

        // Observa el LiveData para actualizar la lista cuando cambien los datos
        plantProfileViewModel.plantProfiles.observe(viewLifecycleOwner) { plantProfiles ->
            plantAdapter.updatePlantProfiles(plantProfiles)
        }


        val fabAddPlant = view.findViewById<FloatingActionButton>(R.id.fab_add_plant)
        fabAddPlant.setOnClickListener {
            // Iniciar la navegación hacia AddPlantFragment usando el NavController
            findNavController().navigate(R.id.addPlantFragment)
        }

        return view
    }
}
