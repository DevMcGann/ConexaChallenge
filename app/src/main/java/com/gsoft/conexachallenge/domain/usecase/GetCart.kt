package com.gsoft.conexachallenge.domain.usecase

import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.data.repository.ProductRepository
import com.gsoft.conexachallenge.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCart @Inject constructor (
    private val apiRepository: ProductRepository
) {
    suspend operator fun invoke() : Flow<List<ProductDB>> {
        return  apiRepository.getCartProducts()
    }
}