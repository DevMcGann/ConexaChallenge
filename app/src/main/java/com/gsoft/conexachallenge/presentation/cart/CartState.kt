package com.gsoft.conexachallenge.presentation.cart

import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB

data class CartState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val data : List<ProductDB> = emptyList(),
    val cartSize : Int = 0,
    val total : Double = 0.0
)
