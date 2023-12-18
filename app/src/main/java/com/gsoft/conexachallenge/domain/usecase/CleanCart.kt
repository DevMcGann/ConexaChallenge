package com.gsoft.conexachallenge.domain.usecase

import com.gsoft.conexachallenge.data.repository.ProductRepository
import javax.inject.Inject

class CleanCart  @Inject constructor (
    private val apiRepository: ProductRepository
) {
    suspend operator fun invoke()  {
        apiRepository.clearCart()
    }
}