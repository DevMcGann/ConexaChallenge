package com.gsoft.conexachallenge.domain.usecase

import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.data.repository.ProductRepository
import javax.inject.Inject

class RestProduct  @Inject constructor (
    private val apiRepository: ProductRepository
) {
    suspend operator fun invoke(productDB: ProductDB)  {
        apiRepository.removeProductFromCart(productDB)
    }
}