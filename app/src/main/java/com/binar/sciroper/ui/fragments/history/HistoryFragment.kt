package com.binar.sciroper.ui.fragments.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyVm: HistoryVm by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyVm.getHistory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            historyFragment = this@HistoryFragment
        }

        historyVm.historyList.observe(viewLifecycleOwner) {
            val adapter = HistoryAdapter(it.reversed())
            binding.recylclerView.adapter = adapter
        }

        historyVm.loadInd.observe(viewLifecycleOwner) {
            binding.loadingInd.isGone = it != true
        }
    }

    fun navToMenu() {
        val action = HistoryFragmentDirections.actionHistoryFragmentToMenuFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}