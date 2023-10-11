package com.example.market.ui.shopping_cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market.data.DataMockViewModel
import com.example.market.databinding.FragmentShoppingCartBinding
import com.example.market.ui.products.Adapter.ProductAdapter
import com.example.market.ui.shopping_cart.adapter.ProductCartAdapter
import javax.inject.Inject

class ShoppingCartFragment @Inject constructor(): Fragment() {

    private val dataMockViewModel by viewModels<DataMockViewModel>()

    private var _binding: FragmentShoppingCartBinding? = null

    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductCartAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }
    private fun setAdapter() {
        productAdapter = ProductCartAdapter(dataMockViewModel.cartProducts) {
//            findNavController().navigate(
//                ProductFragmentDirections.actionProductFragmentToProductSelectedActivity(it)
//            )
        }
        binding.rvProductsCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }
        setDataMock()
    }

    private fun setDataMock() {
        productAdapter.updateProducts(dataMockViewModel.cartProducts)
    }

}