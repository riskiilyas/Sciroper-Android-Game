package com.binar.sciroper.ui.fragments.leaderboard


import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.binar.sciroper.databinding.FragmentLeaderboardBinding
import com.binar.sciroper.util.Share
import java.io.File


class LeaderboardFragment : Fragment() {
    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!
    private val leaderBoardVm: LeaderBoardVm by viewModels {
        LeaderBoardVmFactory()
    }
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rcPlayer
        val adapter = UserListAdapter(leaderBoardVm)
        recyclerView.adapter = adapter


        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = leaderBoardVm
        }

        binding.btnBack.setOnClickListener {
            val action = LeaderboardFragmentDirections.actionLeaderboardFragmentToMenuFragment()
            findNavController().navigate(action)
        }

        binding.btnShare.setOnClickListener {
            ambilScreenshot()
            bagiScreenshot()
        }

    }

    private fun ambilScreenshot() {
        val bitmap: Bitmap? = Share.getViewScreenshot(
            binding.constView,
            binding.constView.height,
            binding.constView.width
        )
        bitmap?.let {
            Share.saveScreenshot(it, requireContext())
        }
    }

    private fun bagiScreenshot() {
        val fileScreenshot =
            File(requireContext().getExternalFilesDir(null)!!.absolutePath + "/Screenshot/Screenshot.jpg")
        startActivity(Share.shareFileScreensshot(fileScreenshot))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}