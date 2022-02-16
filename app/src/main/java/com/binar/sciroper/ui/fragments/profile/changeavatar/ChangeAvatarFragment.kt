package com.binar.sciroper.ui.fragments.profile.changeavatar

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.databinding.FragmentChangeAvatarBinding

import com.binar.sciroper.util.App
import com.binar.sciroper.util.AvatarHelper

@SuppressLint("ResourceAsColor")
class ChangeAvatarFragment : Fragment() {
    private var _binding: FragmentChangeAvatarBinding? = null
    private val binding get() = _binding!!
    private var selectedId: Int = 0
    private lateinit var itemList: List<View>
    private val CAVM: ChangeAvatarFragmentVm by viewModels {
        CAVMFactory(App.appDb.getUserDao())
    }
    private val avatarList = AvatarHelper.provideList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeAvatarBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.changeFragment = this@ChangeAvatarFragment
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            binding.changeFragment = this@ChangeAvatarFragment
        }

        itemList = mutableListOf(
            binding.avatarI1,
            binding.avatarI2,
            binding.avatarI3,
            binding.avatarI4,
            binding.avatarI5,
            binding.avatarI6,
            binding.avatarI7,
            binding.avatarI8,
            binding.avatarI9,
            binding.avatarI10,
            binding.avatarI11,
            binding.avatarI12,
        )

        itemList.forEachIndexed { i, it ->
            if (it.isEnabled) {
                it.setOnClickListener {
                    selectItem(i)
                }
            }
        }


        CAVM.userData.observe(viewLifecycleOwner) {
            it.items.let { i ->
                if (i.contains('a')) {
                    isOwned(binding.avatarI5)
                } else {
                    isNoOwned(binding.avatarI5)
                }
                if (i.contains('b')) {
                    isOwned(binding.avatarI6)
                } else {
                    isNoOwned(binding.avatarI6)
                }
                if (i.contains('c')) {
                    isOwned(binding.avatarI7)
                } else {
                    isNoOwned(binding.avatarI7)
                }
                if (i.contains('d')) {
                    isOwned(binding.avatarI8)
                } else {
                    isNoOwned(binding.avatarI8)
                }
                if (i.contains('e')) {
                    isOwned(binding.avatarI9)
                } else {
                    isNoOwned(binding.avatarI9)
                }
                if (i.contains('f')) {
                    isOwned(binding.avatarI10)
                } else {
                    isNoOwned(binding.avatarI10)
                }
                if (i.contains('g')) {
                    isOwned(binding.avatarI11)
                } else {
                    isNoOwned(binding.avatarI11)
                }
                if (i.contains('h')) {
                    isOwned(binding.avatarI12)
                } else {
                    isNoOwned(binding.avatarI12)
                }
            }
            avatarList.forEachIndexed { index, i ->
                if (i == it.avatarId) {
                    itemList[index].setBackgroundColor(R.color.navigationColour)
                    selectedId = index
                    Log.d("ban", "onViewCreated: $index")
                }
            }
        }

    }

    private fun selectItem(i: Int) {
        if (selectedId == i) return
        itemList[selectedId].setBackgroundColor(Color.WHITE)
        itemList[i].setBackgroundColor(R.color.navigationColour)
        selectedId = i
    }

    private fun isOwned(view: View) {
        view.setBackgroundColor(Color.WHITE)
        view.isEnabled = true
        view.isClickable = true
    }

    private fun isNoOwned(view: View) {
        view.setBackgroundColor(Color.GRAY)
        view.isEnabled = false
        view.isClickable = false
    }

    fun back() {
        val action = ChangeAvatarFragmentDirections.actionChangeAvatarFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    fun change() {
        val action = ChangeAvatarFragmentDirections.actionChangeAvatarFragmentToProfileFragment()
        CAVM.changeAvatar(avatarList[selectedId])
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}