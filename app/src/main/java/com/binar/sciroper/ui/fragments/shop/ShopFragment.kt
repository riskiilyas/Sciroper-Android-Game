package com.binar.sciroper.ui.fragments.shop

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.binar.sciroper.R
import com.binar.sciroper.databinding.FragmentShopBinding
import com.binar.sciroper.util.App


class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private val shopVm: ShopVm by viewModels {
        ShopVmFactory(App.appDb.getUserDao())
    }
    private lateinit var itemList: List<View>
    private lateinit var priceList: List<TextView>
    private var selectedId: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        itemList = mutableListOf(
            binding.avatarI1,
            binding.avatarI2,
            binding.avatarI3,
            binding.avatarI4,
            binding.avatarI5,
            binding.avatarI6,
            binding.avatarI7,
            binding.avatarI8
        )

        priceList = mutableListOf(
            binding.textView11,
            binding.textView22,
            binding.textView33,
            binding.textView44,
            binding.textView55,
            binding.textView66,
            binding.textView77,
            binding.textView88
        )

        itemList.forEachIndexed { i, it ->
            if (it.isEnabled) {
                it.setOnClickListener {
                    selectItem(i)
                }
            }
        }

        shopVm.userData.observe(viewLifecycleOwner) {
            it.items.let { i ->
                if (i.contains('a')) isOwned(binding.avatarI1)
                if (i.contains('b')) isOwned(binding.avatarI2)
                if (i.contains('c')) isOwned(binding.avatarI3)
                if (i.contains('d')) isOwned(binding.avatarI4)
                if (i.contains('e')) isOwned(binding.avatarI5)
                if (i.contains('f')) isOwned(binding.avatarI6)
                if (i.contains('g')) isOwned(binding.avatarI7)
                if (i.contains('h')) isOwned(binding.avatarI8)
            }
            binding.tvCoinShop.text = it.coin.toString()
        }

        binding.btnBuy.setOnClickListener {
            val priceListInt = listOf(100, 150, 250, 500, 1000, 1500, 3000, 5000)
            when {
                selectedId == null -> Toast.makeText(requireContext(), "Choose the item first!", Toast.LENGTH_SHORT).show()
                shopVm.userData.value!!.coin < priceListInt[selectedId!!] -> {
                    Toast.makeText(requireContext(), "Not Enough coins!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    shopVm.buyItem("abcdefgh"[selectedId!!], priceListInt[selectedId!!])
                    Toast.makeText(requireContext(), "Item purchased!!", Toast.LENGTH_SHORT).show()
                    selectedId = null
                }
            }
        }

    }

    @SuppressLint("ResourceAsColor")
    private fun selectItem(i: Int){
        selectedId?.let {
            if (it == i) return
            itemList[it].setBackgroundColor(Color.WHITE)
            priceList[it].setTextColor(Color.BLACK)
        }
        itemList[i].setBackgroundColor(R.color.navigationColour)
        priceList[i].setTextColor(Color.WHITE)
        selectedId = i
    }

    private fun isOwned(view: View) {
        view.setBackgroundColor(Color.GRAY)
        view.isEnabled = false
        view.isClickable = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun back() {
        findNavController().navigate(R.id.action_shopFragment_to_menuFragment)
    }
}