package com.example.market.ui.products.Adapter

import android.text.Layout.Alignment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.market.R
import com.example.market.databinding.ItemProductBinding
import com.example.market.ui.products.model.ProductViewModel

class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var binding = ItemProductBinding.bind(view)
    fun render(product: ProductViewModel, onItemSelected: (product: ProductViewModel) -> Unit){
        binding.tvProductName.text = product.product.name
        binding.tvProductDescription.text = product.product.description
        checkCart(product, onItemSelected)
        checkStock(product)
    }

    private fun checkStock(product: ProductViewModel) {
        if (product.product.stock) {
            binding.tvProductUnit.visibility = View.VISIBLE
            binding.tvProductUnit.text = product.product.unitTypeName
            binding.tvProductPrice.text = product.product.price.toString()

        } else {
            binding.tvProductUnit.visibility = View.GONE
            binding.tvProductPrice.text = "SIN STOCK"
            binding.lyProductItem.setBackgroundColor(
                ContextCompat.getColor(
                    binding.lyProductItem.context,
                    R.color.danger
                )
            )
        }
    }

    private fun checkCart(
        product: ProductViewModel,
        onItemSelected: (product: ProductViewModel) -> Unit
    ) {
        if (product.isCart) {
            binding.lyProductItem.setBackgroundColor(
                ContextCompat.getColor(
                    binding.lyProductItem.context,
                    R.color.gold
                )
            )
        } else {
            binding.lyProductItem.setOnClickListener { onItemSelected(product) }
        }
    }
}