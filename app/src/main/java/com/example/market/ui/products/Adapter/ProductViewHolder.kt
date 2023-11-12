package com.example.market.ui.products.Adapter

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.market.R
import com.example.market.databinding.ItemProductBinding
import com.example.market.ui.products.model.ProductViewModel

class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private var binding = ItemProductBinding.bind(view)
    fun render(product: ProductViewModel, onItemSelected: (id: Int) -> Unit){
        binding.tvProductName.text = product.product.name
        binding.tvProductDescription.text = product.product.description
        binding.tvProductUnit.text = product.product.unitType.description
        binding.tvProductPrice.text = product.product.price.toString()

        if (product.isCart){
            binding.lyProductItem.setBackgroundColor(ContextCompat.getColor(binding.lyProductItem.context, R.color.gold))
        } else {
            binding.lyProductItem.setOnClickListener{onItemSelected(product.product.id)}
        }

    }
}