package com.example.market.database.responses

import com.example.market.ui.products.model.ProductViewModel

data class ProductoInternoResponse(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val codigoReferencia: String? = null,
    val distribuidoraReferenciaCodigo: String? = null,
    val distribuidoraReferenciaNombre: String? = null,
    val referenciaNombre: String? = null,
    val category: LookupValueResponse,
    val precioTransporte: Double,
    val precioEmpaquetado: Double,
    val porcentajeGanancia: Double
)

fun ProductoInternoResponse.toViewModel(): ProductViewModel {
    val product: ProductViewModel = ProductViewModel(
        id = id,
        name = nombre,
        description = descripcion,
        price = precio.toInt()
    )
    return product
}