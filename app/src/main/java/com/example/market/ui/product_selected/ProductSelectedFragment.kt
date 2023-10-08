package com.example.market.ui.product_selected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.market.data.DataMockViewModel
import com.example.market.databinding.FragmentProductSelectedBinding

class ProductSelectedFragment : Fragment() {

    private val dataMockViewModel: DataMockViewModel = DataMockViewModel()

    private var _binding: FragmentProductSelectedBinding? = null

    private val binding get() = _binding!!

    private val args: ProductSelectedFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductSelectedBinding.inflate(layoutInflater, container, false)
        setProductSelected()
        return binding.root
    }

    private fun setProductSelected() {
        val productSelected = dataMockViewModel.getById(args.id)

        binding.tvProductSelectedName.text = productSelected.name
        binding.tvProductSelectedDescription.text = productSelected.description
        binding.tvProductSelectedUnit.text = productSelected.unit
        binding.tvProductSelectedPrice.text = productSelected.price.toString()
    }
}