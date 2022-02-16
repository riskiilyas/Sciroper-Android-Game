package com.binar.sciroper.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.data.local.AppSharedPreference
import com.binar.sciroper.databinding.FragmentProfileBinding
import com.binar.sciroper.ui.fragments.profile.DialogUpdate.Companion.DIALOG_UPDATE
import com.binar.sciroper.util.App
import okhttp3.MultipartBody

class ProfileFragment : Fragment() {

    private val profileVm: ProfileVm by viewModels {
        ProfileVmFactory(App.appDb.getUserDao())
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var avatars = 0

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
        binding.apply {
            vm = profileVm
            lifecycleOwner = viewLifecycleOwner
            profileFragment = this@ProfileFragment
        }

        profileVm.userLive.observe(viewLifecycleOwner) {
            binding.ivAvatar.setImageResource(it.avatarId)
            avatars = it.avatarId
        }

        binding.btnSignOut.setOnClickListener {
            onSignOut()
        }


        binding.btnUpdate.setOnClickListener {
            profileVm.postChanges(
                "Bearer ${AppSharedPreference.userToken}",
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("email", binding.etEmail.text.toString())
                    .addFormDataPart("username", binding.etUsername.text.toString())
                    .build(),
                email = binding.etEmail.text.toString(),
                username = binding.etUsername.text.toString(),
                avatarId = avatars
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
        val dialogUpdate = DialogUpdate()
        dialogUpdate.show(childFragmentManager, DIALOG_UPDATE)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}