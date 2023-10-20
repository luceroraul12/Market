package com.example.market.database.responses

import java.util.Date

data class ProductoInternoResponse(
    val id: Int? = null,
    val nombre: String? = null,
    val descripcion: String? = null,
    val precio: Double? = null,
    val codigoReferencia: String? = null,
    val distribuidoraReferenciaCodigo: String? = null,
    val distribuidoraReferenciaNombre: String? = null,
    val referenciaNombre: String? = null,
    val category: LookupValueResponse,
    val precioTransporte: Double,
    val precioEmpaquetado: Double,
    val porcentajeGanancia: Double
)