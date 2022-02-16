package com.binar.sciroper.ui.fragments.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.FragmentProfileBinding
import com.binar.sciroper.util.App
import com.binar.sciroper.util.AvatarHelper
import okhttp3.MultipartBody

class ProfileFragment : Fragment() {

    private val profileVm: ProfileVm by viewModels {
        ProfileVmFactory(App.appDb.getUserDao())
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var avatars: List<ImageView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        avatars = listOf(
//            binding.avatarId1,
//            binding.avatarId2,
//            binding.avatarId3,
//            binding.avatarId4
//        )

        binding.apply {
            vm = profileVm
            lifecycleOwner = viewLifecycleOwner
            profileFragment = this@ProfileFragment
        }

        binding.btnSignOut.setOnClickListener {
            onSignOut()
        }

//        onSelectedAvatar(avatars)

        binding.btnUpdate.setOnClickListener {
            Log.i("button", "button is clicked")
            profileVm.postChanges(
                "Bearer ${AppSharedPreference.userToken}",
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("email", binding.etEmail.text.toString())
                    .addFormDataPart("username", binding.etUsername.text.toString())
                    .build(),
                email = binding.etEmail.text.toString(),
                username = binding.etUsername.text.toString(),
                /*masukkan value avatarId yang di pass lewat nav arg
                * value R.drawable.avatar22 hanya sebuah temporary place holder*/
                avatarId = R.drawable.avatar22
            )
        }

        profileVm.onSuccess.observe(viewLifecycleOwner) {
            if (it) {
                createDialog()
                profileVm.setOnSuccessValue()
            }
        }

        profileVm.onFailure.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        profileVm.loadingInd.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.loadingInd.isVisible = true
            } else {
                binding.loadingInd.isGone = false
            }
        }
    }

//    private fun onSelectedAvatar(avatarList: List<ImageView>) {
//        avatarList.forEachIndexed { index: Int, imageView: ImageView ->
//            imageView.setOnClickListener {
//                profileVm.setAvatarId(AvatarHelper.provideList()[index])
//                avatarList.forEach {
//                    it.setBackgroundResource(android.R.color.transparent)
//                }
//                avatarList[index].setBackgroundResource(R.color.navigationColour)
//            }
//        }
//    }

    private fun onSignOut() {
        val dialogSignOut = DialogSignOutt(profileVm)
        dialogSignOut.show(childFragmentManager, DialogSignOutt.DIALOG_SIGNOUTT)
    }

    fun navToSetting() {
        val action = ProfileFragmentDirections.actionProfileFragmentToSettingFragment()
        findNavController().navigate(action)
    }
    fun navToChangeAvatar() {
        val action = ProfileFragmentDirections.actionProfileFragmentToChangeAvatarFragment()
        findNavController().navigate(action)
    }


    fun createDialog() {
        /*create update success dialog/toast*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}