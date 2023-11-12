package com.example.market.ui.shopping_cart.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.market.databinding.ItemProductCartBinding
import com.example.market.ui.products.model.ProductViewModel
import java.text.DecimalFormat

class ProductCartViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private var binding = ItemProductCartBinding.bind(view)

    fun render(p: ProductViewModel, onItemSelected: (id: Int) -> Unit) {
        binding.tvProductName.text = p.product.name
        binding.tvProductDescription.text = p.product.description
        binding.tvProductUnit.text = generateAmountLabel(p.currentAmount)
        binding.tvProductPrice.text = generatePriceLabel(p.currentPrice)

        binding.lyProductItem.setOnClickListener{onItemSelected(p.product.id)}
    }

    fun generateAmountLabel(amount: Int): String {
        return "x${amount}g"
    }

    fun generatePriceLabel(price: Double): String{
        // Quitar decimales
        val format = DecimalFormat("0.#")

        // Para fraccion
        val currentPrice: String = format.format(price)
        return currentPrice
    }
}