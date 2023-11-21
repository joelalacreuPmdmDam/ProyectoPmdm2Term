package es.jac.gymlog.Fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import es.jac.gymlog.R
import es.jac.gymlog.databinding.FragmentSquatBinding


class SquatFragment : Fragment() {

    private lateinit var binding: FragmentSquatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSquatBinding.inflate(inflater,container,false)


        // Obtén la URI del video en res/raw/
        val videoPath = "android.resource://" + requireContext().packageName + "/" + R.raw.video_squat
        val videoUri = Uri.parse(videoPath)

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoSquat)
        binding.videoSquat.setMediaController(mediaController)

        binding.videoSquat.setVideoURI(videoUri)
        binding.videoSquat.start()
        return binding.root
    }




}