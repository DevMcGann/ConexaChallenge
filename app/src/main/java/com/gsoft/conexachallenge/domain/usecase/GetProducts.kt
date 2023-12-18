package com.gsoft.conexachallenge.domain.usecase

import com.gsoft.conexachallenge.data.repository.ProductRepository
import com.gsoft.conexachallenge.domain.model.Product
import com.gsoft.conexachallenge.util.Resource
import javax.inject.Inject

class GetProducts @Inject constructor (
   private val apiRepository: ProductRepository
) {
    suspend operator fun invoke() : Resource<List<Product>> {
        return  apiRepository.getProducts()
    }
}