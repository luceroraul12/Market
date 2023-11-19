package com.example.market.ui.products.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.market.R
import com.example.market.ui.products.model.ProductViewModel

class ProductAdapter(
    private var products: List<ProductViewModel> = emptyList(),
    private val onItemSelected: (id: ProductViewModel) -> Unit
): RecyclerView.Adapter<ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.render(products.get(position)) {onItemSelected(it)}
    }

    fun updateProducts(list: List<ProductViewModel>){
        products = list
        notifyDataSetChanged()
    }
}