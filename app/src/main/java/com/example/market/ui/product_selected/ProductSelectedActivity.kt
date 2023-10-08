package com.example.market.ui.product_selected

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.navArgs
import com.example.market.R
import com.example.market.data.DataMock
import com.example.market.databinding.ActivityProductSelectedBinding

class ProductSelectedActivity : AppCompatActivity() {

    private val dataMock: DataMock = DataMock()

    private lateinit var binding: ActivityProductSelectedBinding

    private val args: ProductSelectedActivityArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductSelectedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setProductSelected()
    }

    private fun setProductSelected() {
        val productSelected = dataMock.getById(args.id)

        binding.tvProductSelectedName.text = productSelected.name
        binding.tvProductSelectedDescription.text = productSelected.description
        binding.tvProductSelectedUnit.text = productSelected.unit
        binding.tvProductSelectedPrice.text = productSelected.price.toString()
    }
}