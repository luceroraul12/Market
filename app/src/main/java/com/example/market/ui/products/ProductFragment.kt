package com.example.market.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
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
            if (it.product.stock) {
                findNavController().navigate(
                    ProductFragmentDirections.actionProductFragmentToProductSelectedActivity(
                        it.product.id,
                        true
                    )
                )
            }
        }
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }
        val svProducts: SearchView = binding.svProducts
        svProducts.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { searchSpecificProducs(query) }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrBlank())
                        setAllData()
                    return false
                }

            }
        )
        setAllData()
    }
    private fun searchSpecificProducs(search: String): Unit {
        CoroutineScope(Dispatchers.IO).launch {
            val products: List<ProductViewModel> = dataProductViewModel.getProductsBySearch(search)
            withContext(Dispatchers.Main) {
                productAdapter.updateProducts(products)
            }
        }
    }

    private fun setAllData() {
        CoroutineScope(Dispatchers.IO).launch {
            val products: List<ProductViewModel> = dataProductViewModel.getAllProductaWithStatus();
            withContext(Dispatchers.Main) {
                productAdapter.updateProducts(products)
            }
        }
    }
}