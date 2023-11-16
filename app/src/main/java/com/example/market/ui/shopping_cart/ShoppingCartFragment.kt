package com.example.market.ui.shopping_cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market.data.DataProductViewModel
import com.example.market.databinding.FragmentShoppingCartBinding
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

    private val dataProductViewModel by viewModels<DataProductViewModel>()

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
        binding.bSendCart.setOnClickListener{cleanProductCart()}
        setDataMock()
    }

    private fun cleanProductCart() {
        CoroutineScope(Dispatchers.IO).launch {
            dataProductViewModel.cleanProductCart()
            binding.lyResult.visibility = View.INVISIBLE
            binding.bSendCart.visibility = View.INVISIBLE
            withContext(Dispatchers.Main){
                productAdapter.updateProducts(emptyList())
            }
        }
    }

    private fun setDataMock() {
        CoroutineScope(Dispatchers.IO).launch {
            val list: List<ProductViewModel> = dataProductViewModel.getProductsCart().map { p ->
                val product = dataProductViewModel.getProductByProductId(p.id).product
                ProductViewModel(
                    product = product,
                    currentAmount = p.amount,
                    currentPrice = p.price
                )
            }
            withContext(Dispatchers.Main) {
                productAdapter.updateProducts(list)
                setResultPrice(list)
            }
        }
    }

    private fun setResultPrice(list: List<ProductViewModel>): Unit {
        if (list.isEmpty()){
            binding.lyResult.visibility = View.GONE
            binding.bSendCart.visibility = View.GONE
        } else {
            val result = if (list.isNotEmpty()) getResultPrice(list) else ""
            binding.tvResult.text = result
        }
    }

    private fun getResultPrice(list: List<ProductViewModel>): String {
        val result: Double = list
            .map { p -> p.currentPrice }
            .reduce{a,b -> a + b}

        return "ARS ${result.toInt()}"
    }

}