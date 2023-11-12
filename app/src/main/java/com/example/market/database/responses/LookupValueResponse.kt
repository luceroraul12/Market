package com.example.market.database.responses

data class LookupValueResponse(
    val id: Int,
    val code: String,
    val description: String,
    val value: String
)