package com.example.market.ui.products.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.market.databinding.ItemProductBinding
import com.example.market.ui.products.model.ProductViewModel

class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var binding = ItemProductBinding.bind(view)
    fun render(product: ProductViewModel, onItemSelected: (product: ProductViewModel) -> Unit){
        binding.tvProductName.text = product.product.name
        binding.tvProductDescription.text = product.product.description
        checkLabels(product, onItemSelected)
//        checkStock(product)
//        checkCart(product, onItemSelected)
    }

    private fun checkLabels(
        product: ProductViewModel,
        onItemSelected: (product: ProductViewModel) -> Unit
    ) {
        if (!product.product.stock){
            binding.tvProductUnit.visibility = View.GONE
            binding.tvProductPrice.text = "SIN STOCK"
        } else if (product.isCart) {
            binding.tvProductUnit.visibility = View.GONE
            binding.tvProductPrice.text = "CARRITO"
        } else {
            setNormalLabels(product)
            binding.lyProductItem.setOnClickListener { onItemSelected(product) }
        }
    }

    private fun setNormalLabels(product: ProductViewModel) {
        binding.tvProductUnit.visibility = View.VISIBLE
        binding.tvProductUnit.text = product.product.unitTypeName
        binding.tvProductPrice.text = product.product.price.toString()
    }

}