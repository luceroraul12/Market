package com.example.market.ui.product_selected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.market.data.DataMockViewModel
import com.example.market.databinding.FragmentProductSelectedBinding
import com.example.market.ui.products.model.ProductViewModel
import java.text.DecimalFormat
import javax.inject.Inject

class ProductSelectedFragment @Inject constructor(): Fragment() {

    private val dataMockViewModel by viewModels<DataMockViewModel>()

    private var _binding: FragmentProductSelectedBinding? = null

    private val binding get() = _binding!!

    private val args: ProductSelectedFragmentArgs by navArgs()

    private var productSelected: ProductViewModel = ProductViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductSelectedBinding.inflate(layoutInflater, container, false)
        setProductSelected()
        setListener()
        return binding.root
    }

    private fun setListener() {
        binding.sProductSelectedCurrentAmount.addOnChangeListener { _, value, _ ->
            setNewCurrentAmount(value)
        }
        binding.bAdd.setOnClickListener { addProductoToCart() }
        binding.bRemove.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setNewCurrentAmount(amount: Float) {
        // Quitar decimales
        val format = DecimalFormat("0.#")

        // Para fraccion
        val currentAmount: String = format.format(amount)
        val labelUnit: String = currentAmount
        binding.tvProductSelectedCurrentAmount.text = labelUnit

        // Para precio MODO DE EJEMPLO suponiendo que todos las unidades se encuentran en 100g
        val priceString: String = binding.tvProductSelectedPrice.text.toString()
        val price: Int = priceString.toInt()

        val amountNormalized = amount / 100
        val currentPrice = (amountNormalized * price)
        binding.tvProductSelectedCurrentPrice.text = format.format(currentPrice)
    }

    private fun setProductSelected() {
        productSelected = dataMockViewModel.getById(args.id)

        binding.tvProductSelectedName.text = productSelected.name
        binding.tvProductSelectedDescription.text = productSelected.description
        binding.tvProductSelectedUnit.text = productSelected.unit
        binding.tvProductSelectedPrice.text = productSelected.price.toString()
    }

    private fun addProductoToCart() {
        productSelected.currentAmount = binding.tvProductSelectedCurrentAmount.text.toString().toInt()
        productSelected.currentPrice = binding.tvProductSelectedCurrentPrice.text.toString().toInt()

        dataMockViewModel.addCartProduct(productSelected)
    }
}