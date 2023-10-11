package com.example.market.ui.shopping_cart.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.market.databinding.ItemProductCartBinding
import com.example.market.ui.products.model.ProductViewModel

class ProductCartViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private var binding = ItemProductCartBinding.bind(view)

    fun render(product: ProductViewModel, onItemSelected: (id: Int) -> Unit) {
        binding.tvProductName.text = product.name
        binding.tvProductDescription.text = product.description
        binding.tvProductUnit.text = product.currentAmount.toString()
        binding.tvProductPrice.text = product.currentPrice.toString()

        binding.lyProductItem.setOnClickListener{onItemSelected(product.id)}

    }
}