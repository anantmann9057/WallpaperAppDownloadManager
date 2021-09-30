package com.example.downloadcoroutines.ui.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.downloadcoroutines.R
import com.example.downloadcoroutines.databinding.FragmentProfileBinding
import com.example.downloadcoroutines.utils.CAMERA_REQUEST_CODE
import com.example.downloadcoroutines.utils.showToast
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        setViews()
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val window: Window = requireActivity().getWindow()
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.deep_purple_900)
    }

    private fun setViews() {
        binding.imageCamera.setOnClickListener {
            captureProfilePic()
        }
        binding.animeProfile.setAnimationFromUrl("https://assets7.lottiefiles.com/packages/lf20_dgsA0b.json")
        binding.animeProfilePic.setAnimationFromUrl("https://assets7.lottiefiles.com/datafiles/6deVuMSwjYosId3/data.json")
    }

    private fun captureProfilePic() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.extras!!.get("data")?.let { requireContext().showToast(it) }
                    binding.imageProfile.setImageBitmap(data.extras!!.get("data") as Bitmap)
                    animeProfilePic.visibility = View.GONE
                }
            }
        }

    }
}