package com.example.market.ui.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market.data.DataProductViewModel
import com.example.market.databinding.FragmentProductBinding
import com.example.market.ui.products.Adapter.ProductAdapter
import com.example.market.ui.products.model.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment @Inject constructor() : Fragment() {

    private val dataProductViewModel by viewModels<DataProductViewModel>()

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        productAdapter = ProductAdapter() {
            findNavController().navigate(
                ProductFragmentDirections.actionProductFragmentToProductSelectedActivity(it, true)
            )
        }
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }
        setDataMock()
    }


    private fun setDataMock() {
        CoroutineScope(Dispatchers.IO).launch {
            val products: List<ProductViewModel>  = dataProductViewModel.getAllProductaWithStatus();
            withContext(Dispatchers.Main) {
                productAdapter.updateProducts(products)
            }
        }
    }



}