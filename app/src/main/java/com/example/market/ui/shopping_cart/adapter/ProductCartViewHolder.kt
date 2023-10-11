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
        binding.tvProductUnit.text = generateAmountLabel(product.currentAmount)
        binding.tvProductPrice.text = generatePriceLabel(product.currentPrice)

        binding.lyProductItem.setOnClickListener{onItemSelected(product.id)}
    }

    fun generateAmountLabel(amount: Int): String {
        return "x${amount}g"
    }

    fun generatePriceLabel(price: Double): String{
        return "$price"
    }
}