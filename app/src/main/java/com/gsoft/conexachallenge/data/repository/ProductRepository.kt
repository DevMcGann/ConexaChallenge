package com.gsoft.conexachallenge.data.repository

import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.domain.model.Product
import com.gsoft.conexachallenge.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(): Resource<List<Product>>

    suspend fun getCartProducts(): Flow<List<ProductDB>>

    suspend fun addProductToCart(productDB: ProductDB)

    suspend fun removeProductFromCart(productDB: ProductDB)

    suspend fun getTotalPrice(): Flow<Double?>

    suspend fun clearCart()

}