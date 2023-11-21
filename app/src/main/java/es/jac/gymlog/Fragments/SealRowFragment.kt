package es.jac.gymlog.Fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import es.jac.gymlog.R
import es.jac.gymlog.databinding.FragmentSealRowBinding


class SealRowFragment : Fragment() {

    private lateinit var binding: FragmentSealRowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSealRowBinding.inflate(inflater,container,false)


        // Obtén la URI del video en res/raw/
        val videoPath = "android.resource://" + requireContext().packageName + "/" + R.raw.video_seal_row
        val videoUri = Uri.parse(videoPath)

        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoRemo)
        binding.videoRemo.setMediaController(mediaController)

        binding.videoRemo.setVideoURI(videoUri)
        binding.videoRemo.start()


        return binding.root
    }


}