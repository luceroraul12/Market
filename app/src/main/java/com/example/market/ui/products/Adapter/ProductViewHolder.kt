package com.example.market.ui.products.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.market.databinding.ItemProductBinding
import com.example.market.ui.products.model.ProductViewModel

class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var binding = ItemProductBinding.bind(view)
    fun render(product: ProductViewModel){
        binding.tvProductName.text = product.name
        binding.tvProductDescription.text = product.description
        binding.tvProductUnit.text = product.unit
        binding.tvProductPrice.text = product.price.toString()
    }
}