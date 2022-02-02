package com.binar.sciroper.ui.mvvm.fragments.vs_com

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.binar.sciroper.R
import com.binar.sciroper.data.db.user.User
import com.binar.sciroper.databinding.FragmentVsComBinding
import com.binar.sciroper.util.App


class VsComFragment : Fragment() {

    private var _binding: FragmentVsComBinding? = null
    private val binding get() = _binding!!
    private val vsComVm: VsComVm by viewModels {
        VsComVmFactory(App.appDb.getUserDao())
    }
    private lateinit var user: User

    private lateinit var p1Choices: List<ImageView>
    private lateinit var comChoices: List<ImageView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVsComBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = vsComVm.user

        p1Choices = listOf(
            binding.ivPemain1BatuPlayer,
            binding.ivPemain1KertasPlayer,
            binding.ivPemain1GuntingPlayer
        )

        comChoices = listOf(
            binding.ivPemain2BatuCOM,
            binding.ivPemain2KertasCOM,
            binding.ivPemain2GuntingCOM
        )

        p1Choices.forEach { its ->
            its.setOnClickListener {
                it.setBackgroundResource(R.drawable.shape_background)
                showToast("${user.username} Memilih ${it.contentDescription}")
                vsComVm.setPlayerChoice(it.contentDescription.toString())
                freezeState(p1Choices)
                repeat(3) {
                    vsComVm.setComputerChoice()
                    comChoices.filter { it.contentDescription == vsComVm.computerChoice }[0].setBackgroundResource(
                        R.drawable.shape_background
                    )
                }
                vsComVm.setOpponentSelectedId(comChoices.filter { it.contentDescription == vsComVm.computerChoice }[0].id)
            }
        }

        binding.apply {
            vm = vsComVm
            lifecycleOwner = viewLifecycleOwner
        }

        binding.apply {
            ivAvatarPlayer.setImageResource(vsComVm.user.avatarId)
        }


    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun freezeState(choices: List<ImageView>) {
        vsComVm.setStatus(false)
        choices.forEach {
            it.isEnabled = vsComVm.status
        }
    }

    private fun View.changeBgColor(@ColorRes id: Int) {
        setBackgroundColor(ContextCompat.getColor(requireActivity(), id))
    }

    fun mockChoices(){

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}