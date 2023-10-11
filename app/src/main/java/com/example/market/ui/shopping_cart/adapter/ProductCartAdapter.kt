package com.example.market.ui.shopping_cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.market.R
import com.example.market.ui.products.Adapter.ProductViewHolder
import com.example.market.ui.products.model.ProductViewModel

class ProductCartAdapter(
    private var products: List<ProductViewModel> = emptyList(),
    private val onItemSelected: (id: Int) -> Unit
): RecyclerView.Adapter<ProductCartViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCartViewHolder {
        return ProductCartViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_cart, parent, false))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductCartViewHolder, position: Int) {
        holder.render(products.get(position)) {onItemSelected(it)}
    }
    fun updateProducts(list: List<ProductViewModel>){
        products = list
        notifyDataSetChanged()
    }
}