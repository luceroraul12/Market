package com.example.market.ui.product_selected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.market.R
import com.example.market.data.DataProductViewModel
import com.example.market.database.entities.ProductCartEntity
import com.example.market.databinding.FragmentProductSelectedBinding
import com.example.market.ui.products.model.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class ProductSelectedFragment @Inject constructor(): Fragment() {

    private val dataProductViewModel by viewModels<DataProductViewModel>()

    private var _binding: FragmentProductSelectedBinding? = null

    private val binding get() = _binding!!

    private val args: ProductSelectedFragmentArgs by navArgs()

    private lateinit var productSelected: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductSelectedBinding.inflate(layoutInflater, container, false)
        setProductSelected()
        setUI()
        setListener()
        return binding.root
    }

    private fun setUI() {
        if(args.newProduct){
            binding.bRemove.visibility = View.GONE
        } else {
            binding.bRemove.visibility = View.VISIBLE
            binding.bAdd.text = getText(R.string.actualizar)
        }
    }

    private fun setListener() {
        binding.sProductSelectedCurrentAmount.addOnChangeListener { _, value, _ ->
            setNewCurrentAmount(value)
        }
        binding.bAdd.setOnClickListener { addProductoToCart() }
        binding.bRemove.setOnClickListener { removeProductCart() }
        binding.bBack.setOnClickListener { requireActivity().onBackPressed() }
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                productSelected = dataProductViewModel.getById(args.id)
                binding.tvProductSelectedName.text = productSelected.product.name
                binding.tvProductSelectedDescription.text = productSelected.product.description
                binding.tvProductSelectedUnit.text = productSelected.product.unitTypeName
                binding.tvProductSelectedPrice.text = productSelected.product.price.toString()
                binding.tvProductSelectedCurrentAmount.text = productSelected.currentAmount.toString()
            }
        }
    }

    private fun addProductoToCart() {
        productSelected.currentAmount = binding.tvProductSelectedCurrentAmount.text.toString().toInt()
        productSelected.currentPrice = binding.tvProductSelectedCurrentPrice.text.toString().toDouble()

        dataProductViewModel.insert(ProductCartEntity(
            id = productSelected.product.id,
            name = productSelected.product.name,
            amount = productSelected.currentAmount,
            price = productSelected.currentPrice,
            description = productSelected.product.description ?: "Sin descripcion"
        ));
        val label = "Carrito: Producto ${if(args.newProduct) "agregado" else "actualizado"}: ${productSelected.product.name}";
        val toast = Toast.makeText(requireContext(), label, Toast.LENGTH_SHORT);
        toast.show()
        requireActivity().onBackPressed()
    }

    private fun removeProductCart(){
        dataProductViewModel.removeProductCart(productSelected.product.id)
        val label = "Carrito: Producto eliminado: ${productSelected.product.name}";
        val toast = Toast.makeText(requireContext(), label, Toast.LENGTH_SHORT);
        toast.show()
        requireActivity().onBackPressed()
    }
}