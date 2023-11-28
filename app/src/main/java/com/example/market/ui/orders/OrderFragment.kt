package com.example.market.ui.orders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.market.R
import com.example.market.data.DataProductViewModel
import com.example.market.database.responses.OrderResponse
import com.example.market.databinding.FragmentOrderBinding
import com.example.market.databinding.FragmentProductSelectedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

class OrderFragment : Fragment() {

    private val viewModel by viewModels<OrderViewModel>()

    private var _binding: FragmentOrderBinding? = null

    private lateinit var orders: List<OrderResponse>

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(layoutInflater, container, false)
        requestOrders()
        return binding.root
    }

    private fun requestOrders() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                orders = viewModel.getMyOrders("PASIONARIA", "ola@gmail.com")
                Log.i("ordenes", orders.toString())
            }
        }
    }

}