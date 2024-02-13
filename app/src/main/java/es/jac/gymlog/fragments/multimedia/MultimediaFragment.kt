package es.jac.gymlog.fragments.multimedia

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import es.jac.gymlog.R
import es.jac.gymlog.databinding.FragmentMultimediaBinding


class MultimediaFragment : Fragment() {

    private lateinit var binding: FragmentMultimediaBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMultimediaBinding.inflate(inflater,container,false)

        binding.btnLoad.setOnClickListener {
            val selectedVideoName = binding.spinnerVideos.selectedItem as String
            prepareVideo(selectedVideoName)
            binding.videoContainer.start()
        }
        return binding.root
    }

    private fun prepareVideo(nameVideo: String){
        val idVideo: Int = resources.getIdentifier(nameVideo,"raw",requireContext().packageName)
        binding.videoContainer.setVideoURI(
            Uri.parse("android.resource://" + requireContext().packageName + "/" + idVideo)
        )
        Log.d("VideoApp", "URI del video: $nameVideo")
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoContainer)
        mediaController.setMediaPlayer(binding.videoContainer)
        binding.videoContainer.setMediaController(mediaController)
    }


}