package com.binar.sciroper.ui.fragments.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.binar.sciroper.databinding.FragmentShopBinding
import com.binar.sciroper.util.App


class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private val shopVm: ShopVm by viewModels {
        ShopVmFactory(App.appDb.getUserDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = shopVm
            shopFragment = this@ShopFragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}