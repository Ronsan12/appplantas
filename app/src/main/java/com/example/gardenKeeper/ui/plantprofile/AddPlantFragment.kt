package com.example.gardenKeeper.ui.plantprofile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gardenkeeper.R

class AddPlantFragment : Fragment() {

    private lateinit var plantImagePreview: ImageView
    private lateinit var uploadImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var viewModel: PlantProfileViewModel
    private lateinit var plantTypeInput: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_plant, container, false)


        viewModel = ViewModelProvider(requireActivity())[PlantProfileViewModel::class.java]

        plantImagePreview = view.findViewById(R.id.plant_image_preview)
        plantTypeInput = view.findViewById(R.id.plant_type_input)
        val uploadImageButton: Button = view.findViewById(R.id.btn_upload_image)
        val savePlantButton: Button = view.findViewById(R.id.btn_save_plant)
        val plantLocationInput: EditText = view.findViewById(R.id.plant_location_input)
        val plantNeedsInput: EditText = view.findViewById(R.id.plant_needs_input)
        val plantObservationsInput: EditText = view.findViewById(R.id.plant_observations_input)

        // Observar los LiveData del ViewModel y reflejarlos en la UI
        viewModel.plantImageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let { plantImagePreview.setImageURI(it) }
        }

        // Configurar el botón para subir la imagen
        uploadImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let {
                    viewModel.setPlantImageUri(it) // Actualizar el ViewModel
                }
            }
        }

        uploadImageButton.setOnClickListener {
            selectImageFromGallery()
        }

        // Lógica para guardar el perfil de la planta
        savePlantButton.setOnClickListener {
            // Obtener los datos de entrada
            val imageUri = viewModel.plantImageUri.value
            val type = plantTypeInput.text.toString()
            val location = plantLocationInput.text.toString()
            val needs = plantNeedsInput.text.toString()
            val observations = plantObservationsInput.text.toString()

            // Guardar el perfil de la planta
            savePlantProfile(imageUri, type, location, needs, observations)
        }

        return view
    }

    private fun savePlantProfile(imageUri: Uri?, type: String, location: String, needs: String, observations: String) {
        val newPlantProfile = PlantProfile(
            imageUri = imageUri,
            type = type,
            location = location,
            needs = needs,
            obs = observations
        )

        // Agregar la nueva planta al ViewModel compartido
        viewModel.addPlantProfile(newPlantProfile)

        // Navegar a PlantProfileFragment
        findNavController().navigate(R.id.nav_plant_profile)
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        uploadImageLauncher.launch(intent)
    }
}




