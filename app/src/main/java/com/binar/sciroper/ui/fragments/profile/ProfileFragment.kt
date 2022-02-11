package com.binar.sciroper.ui.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.FragmentProfileBinding
import com.binar.sciroper.util.App
import com.binar.sciroper.util.AvatarHelper

class ProfileFragment : Fragment() {

    private val profileVm: ProfileVm by viewModels {
        ProfileVmFactory(App.appDb.getUserDao())
    }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User
    private lateinit var avatars: List<ImageView>
    private lateinit var userListExcl: List<User>
    private lateinit var inputUsername: String
    private lateinit var inputEmail: String
    private lateinit var inputPassword: String

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

        avatars = listOf(
            binding.avatarId1,
            binding.avatarId2,
            binding.avatarId3,
            binding.avatarId4
        )

        binding.apply {
            vm = profileVm
            lifecycleOwner = viewLifecycleOwner
            profileFragment = this@ProfileFragment
        }

        profileVm.getUser().observe(viewLifecycleOwner) {
            user = it
            bind(it)
            onSelectAvatar(it, avatars)
        }

        profileVm.userAvatarId.observe(viewLifecycleOwner) {

        }

        binding.btnSignOut.setOnClickListener {
            onSignOut()
        }
    }

    private fun bind(user: User) {
        binding.apply {
            etUsername.setText(user.username, TextView.BufferType.SPANNABLE)
            etEmail.setText(user.email, TextView.BufferType.SPANNABLE)
//            testing.text = user.avatarId.toString()
        }
    }

    private fun onSelectAvatar(user: User, avatarList: List<ImageView>) {
        avatarList.forEachIndexed { index: Int, imageView: ImageView ->
            imageView.setOnClickListener {
                profileVm.setAvatarId(AvatarHelper.provideList()[index])
            }
        }
    }

    private fun onSignOut() {
        val dialogSignOut = DialogSignOutt()
        dialogSignOut.show(childFragmentManager, DialogSignOutt.DIALOG_SIGNOUTT)
    }

    fun navToSetting() {
        val action = ProfileFragmentDirections.actionProfileFragmentToSettingFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}