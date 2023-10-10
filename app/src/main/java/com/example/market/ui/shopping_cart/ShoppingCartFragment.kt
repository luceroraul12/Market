package com.example.market.ui.shopping_cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.market.R
import com.example.market.data.DataMockViewModel
import com.example.market.databinding.FragmentProductSelectedBinding
import com.example.market.databinding.FragmentShoppingCartBinding
import javax.inject.Inject

class ShoppingCartFragment @Inject constructor(): Fragment() {

    private val dataMockViewModel by viewModels<DataMockViewModel>()

    private var _binding: FragmentShoppingCartBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShoppingCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}