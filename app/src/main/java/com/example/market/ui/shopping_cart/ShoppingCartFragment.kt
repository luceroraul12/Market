package com.example.market.ui.shopping_cart

import android.Manifest
import android.accounts.Account
import android.accounts.AccountManager
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.market.R
import com.example.market.data.DataProductViewModel
import com.example.market.databinding.FragmentShoppingCartBinding
import com.example.market.ui.products.model.ProductViewModel
import com.example.market.ui.shopping_cart.adapter.ProductCartAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

@AndroidEntryPoint
class ShoppingCartFragment @Inject constructor(
) : Fragment() {

    companion object {
        const val EMAIL = "email"
    }

    private val dataProductViewModel by viewModels<DataProductViewModel>()

    private var _binding: FragmentShoppingCartBinding? = null

    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductCartAdapter

    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        checkPermission(Manifest.permission.GET_ACCOUNTS)
        checkPermission(Manifest.permission.READ_CONTACTS)
        checkEmailUser()
        _binding = FragmentShoppingCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun checkEmailUser() {
        prepareDialog()
        CoroutineScope(Dispatchers.IO).launch {
            getEmail().take(1).collect { d ->
                requireActivity().runOnUiThread{
                    if (d == null) {
                        dialog.show()
                    }
                }
            }
        }
    }

    private fun prepareDialog() {
        val accounts: Array<Account> =
            AccountManager.get(context).getAccountsByType("com.google")
        dialog = Dialog(requireContext())
        if (accounts.isNotEmpty()) {
            dialog.setContentView(R.layout.dialog_email_select)
            // Genero las opciones por cada correo que tenga el usuario
            val rgEmails: RadioGroup = dialog.findViewById(R.id.rgEmails)
            for ((index, a) in accounts.withIndex()) {
                val radioButton = RadioButton(requireContext())
                radioButton.setText(a.name)
                radioButton.id = index
                rgEmails.addView(radioButton)
            }
            // Boton de aceptacion
            val bAcept: Button = dialog.findViewById(R.id.bEmailSelectAcept)
            bAcept.setOnClickListener {
                val radioButton: RadioButton =
                    dialog.findViewById(rgEmails.checkedRadioButtonId)
                val newEmail: String = radioButton.text.toString()
                Toast.makeText(requireContext(), radioButton.text, Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.IO).launch {
                    saveEmail(newEmail)
                }
                dialog.hide()
            }
            // Boton de cancelacion
            val bCancel: Button = dialog.findViewById(R.id.bEmailSelectCancel)
            bCancel.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Selección de correo cancelada",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.hide()
            }
        }
    }

    private suspend fun saveEmail(email: String): Unit {
        requireContext().dataStore.edit { preference ->
            preference[stringPreferencesKey(EMAIL)] = email
        }
    }

    private fun getEmail(): Flow<String?> {
        return requireContext().dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(EMAIL)]
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }

    private fun setAdapter() {
        productAdapter = ProductCartAdapter() {
            findNavController().navigate(
                ShoppingCartFragmentDirections.actionShoppingCartFragment3ToProductSelectedFragment(
                    it,
                    false
                )
            )
        }
        binding.rvProductsCart.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }
        binding.bSendCart.setOnClickListener { cleanProductCart() }
        setDataMock()
    }

    private fun cleanProductCart() {
        CoroutineScope(Dispatchers.IO).launch {
            dataProductViewModel.cleanProductCart()
            binding.lyResult.visibility = View.INVISIBLE
            binding.bSendCart.visibility = View.INVISIBLE
            withContext(Dispatchers.Main) {
                productAdapter.updateProducts(emptyList())
                notifySeller()
            }
        }
    }

    private fun notifySeller() {
        val number: String = "+542657678661"
        CoroutineScope(Dispatchers.IO).launch {
            getEmail().take(1).collect { d ->
                val buyId: Int = 20
                val message: String = "Hola soy el usuario $d y te acabo de hacer el pedido con identificador N° $buyId"
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=$number&text=${message.toUri()}")
                )
                startActivity(intent)
            }
        }

    }

    private fun checkPermission(permissionCode: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permissionCode
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            //El permiso no está aceptado.
            requestPermission(permissionCode)
        } else {
            //El permiso está aceptado.
        }
    }

    private fun requestPermission(permissionCode: String) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                permissionCode
            )
        ) {
            //El usuario ya ha rechazado el permiso anteriormente, debemos informarle que vaya a ajustes.
        } else {
            //El usuario nunca ha aceptado ni rechazado, así que le pedimos que acepte el permiso.
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(permissionCode),
                123
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            123 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //El usuario ha aceptado el permiso, no tiene porqué darle de nuevo al botón, podemos lanzar la funcionalidad desde aquí.
                } else {
                    //El usuario ha rechazado el permiso, podemos desactivar la funcionalidad o mostrar una vista/diálogo.
                }
                return
            }

            321 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //El usuario ha aceptado el permiso, no tiene porqué darle de nuevo al botón, podemos lanzar la funcionalidad desde aquí.
                } else {
                    //El usuario ha rechazado el permiso, podemos desactivar la funcionalidad o mostrar una vista/diálogo.
                }
                return
            }

            else -> {
                // Este else lo dejamos por si sale un permiso que no teníamos controlado.
            }
        }
    }

    private fun setDataMock() {
        binding.pb.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val list: List<ProductViewModel> = dataProductViewModel.getProductsCart().map { p ->
                val product = dataProductViewModel.getProductByProductId(p.id).product
                ProductViewModel(
                    product = product,
                    currentAmount = p.amount,
                    currentPrice = p.price
                )
            }
            withContext(Dispatchers.Main) {
                productAdapter.updateProducts(list)
                setResultPrice(list)
                binding.pb.visibility = View.GONE
            }
        }
    }

    private fun setResultPrice(list: List<ProductViewModel>): Unit {
        if (list.isEmpty()) {
            binding.lyResult.visibility = View.GONE
            binding.bSendCart.visibility = View.GONE
        } else {
            val result = if (list.isNotEmpty()) getResultPrice(list) else ""
            binding.tvResult.text = result
        }
    }

    private fun getResultPrice(list: List<ProductViewModel>): String {
        val result: Double = list
            .map { p -> p.currentPrice }
            .reduce { a, b -> a + b }

        return "ARS ${result.toInt()}"
    }

}