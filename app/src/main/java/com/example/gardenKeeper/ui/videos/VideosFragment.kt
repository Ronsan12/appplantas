package com.example.gardenkeeper.ui.video

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.example.gardenkeeper.R

class VideoFragment : Fragment() {

    private lateinit var videoView: VideoView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_video, container, false)


        videoView = view.findViewById(R.id.videoView)


        val videoUri: Uri = Uri.parse("android.resource://${requireActivity().packageName}/${R.raw.plantas}")
        videoView.setVideoURI(videoUri)


        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)


        videoView.setOnPreparedListener { mediaPlayer: MediaPlayer ->
            mediaPlayer.isLooping = true
            videoView.start()
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

    override fun onResume() {
        super.onResume()
        videoView.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        videoView.stopPlayback()
    }
}

