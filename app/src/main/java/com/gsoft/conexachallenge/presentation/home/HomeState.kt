package com.gsoft.conexachallenge.presentation.home

import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.domain.model.Product

data class HomeState(
    val isLoading: Boolean = false,
    val data : List<Product> = emptyList(),
    val error : String = "",
    val noInternet : Boolean = false,
    val cartProducts : List<ProductDB> = emptyList(),
    val categories : List<String> = emptyList(),
    val isError : Boolean = false,
    val isCartWithItems : Boolean = false,
    val cartItemCount : Int = 0,
    val selectedCategory : String = "",
    val isCategoryModalVisible : Boolean = false
)
