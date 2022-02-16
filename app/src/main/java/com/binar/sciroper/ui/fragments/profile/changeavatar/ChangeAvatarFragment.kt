package com.binar.sciroper.ui.fragments.profile.changeavatar

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.databinding.FragmentChangeAvatarBinding
import com.binar.sciroper.databinding.FragmentProfileBinding

class ChangeAvatarFragment : Fragment() {
    private var _binding: FragmentChangeAvatarBinding? = null
    private val binding get() = _binding!!
    private var selectedId: Int? = null
    private lateinit var itemList: List<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeAvatarBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.changeFragment = this@ChangeAvatarFragment

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
    }

    @SuppressLint("ResourceAsColor")
    private fun selectItem(i: Int){
        selectedId?.let {
            if (it == i) return
            itemList[it].setBackgroundColor(Color.WHITE)

        }
        itemList[i].setBackgroundColor(R.color.navigationColour)
        selectedId = i
    }

    fun back() {
        val action = ChangeAvatarFragmentDirections.actionChangeAvatarFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}