package com.binar.sciroper.ui.mvvm.fragments.leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.binar.sciroper.databinding.FragmentLeaderboardBinding
import com.binar.sciroper.util.App
import com.binar.sciroper.util.UserLevel

class LeaderboardFragment : Fragment() {

    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!
    private val leaderBoardVm: LeaderBoardVm by viewModels {
        LeaderBoardVmFactory(App.appDb.getUserDao())
    }
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.rcPlayer
        val adapter = UserListAdapter()
        recyclerView.adapter = adapter


        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = leaderBoardVm
        }


        leaderBoardVm.allUsers.observe(viewLifecycleOwner) { users ->
            users.let {
                adapter.submitList(UserLevel.sortUsersLevel(it))
            }
            leaderBoardVm.getUserListSize(users)
        }

        binding.btnBack.setOnClickListener {
            val action = LeaderboardFragmentDirections.actionLeaderboardFragmentToMenuFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}