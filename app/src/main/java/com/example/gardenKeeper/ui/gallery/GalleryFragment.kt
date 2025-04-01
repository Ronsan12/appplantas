package com.example.gardenkeeper.ui.gallery

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gardenkeeper.R
import com.example.gardenkeeper.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    private lateinit var galleryViewModel: GalleryViewModel

    private val images = mutableListOf<Any>(
        R.drawable.add, R.drawable.add, R.drawable.add,
        R.drawable.add, R.drawable.add, R.drawable.add,
        R.drawable.add, R.drawable.add, R.drawable.add,
        R.drawable.add, R.drawable.add, R.drawable.add
    )

    private lateinit var getImageLauncher: ActivityResultLauncher<String>
    private lateinit var adapter: GalleryAdapter

    private var currentImageIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        galleryViewModel = ViewModelProvider(this)[GalleryViewModel::class.java]
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val gridLayoutManager = GridLayoutManager(context, 3)
        binding.galleryRecyclerView.layoutManager = gridLayoutManager

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
        binding.galleryRecyclerView.addItemDecoration(
            GridSpacingItemDecoration(3, spacingInPixels, true)
        )

        adapter = GalleryAdapter(images)
        binding.galleryRecyclerView.adapter = adapter

        getImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                if (currentImageIndex < images.size) {
                    images[currentImageIndex] = it
                    adapter.notifyItemChanged(currentImageIndex)
                    currentImageIndex++
                }
            }
        }

        binding.profileImage.setOnClickListener {
            changeProfileImageAndText()
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.equals("Flor de lis", ignoreCase = true)) {
                    changeProfileImageAndText()
                }
                return false
            }
        })

        binding.uploadImageButton.setOnClickListener {
            openGalleryForImage()
        }

        return root
    }

    private fun changeProfileImageAndText() {
        binding.profileImage.setImageResource(R.drawable.plant)

        binding.locationLabel.text = "Ubicación: Jardín delantero."
        binding.needsLabel.text = "Necesidades: Regar 2 veces al día."
        binding.observationsLabel.text = "Observaciones: Reubicar en patio trasero."
    }

    private fun openGalleryForImage() {
        getImageLauncher.launch("image/*")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
