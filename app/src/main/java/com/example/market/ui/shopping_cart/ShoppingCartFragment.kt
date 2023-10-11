package com.example.market.ui.shopping_cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market.data.DataMockViewModel
import com.example.market.databinding.FragmentShoppingCartBinding
import com.example.market.ui.products.Adapter.ProductAdapter
import com.example.market.ui.products.model.ProductViewModel
import com.example.market.ui.shopping_cart.adapter.ProductCartAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingCartFragment @Inject constructor(
): Fragment() {

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
        productAdapter = ProductCartAdapter() {
            findNavController().navigate(
                ShoppingCartFragmentDirections.actionShoppingCartFragment3ToProductSelectedFragment(it, false)
            )
        }
        binding.rvProductsCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }
        setDataMock()
    }

    private fun setDataMock() {
        CoroutineScope(Dispatchers.IO).launch {
            val list = dataMockViewModel.getProductsCart().map { p ->
                ProductViewModel(
                    id = p.id,
                    name = p.name,
                    currentPrice = p.price,
                    currentAmount = p.amount,
                    description = p.description
                )
            }
            withContext(Dispatchers.Main) {
                productAdapter.updateProducts(list)
            }
        }
    }

}