package com.gsoft.conexachallenge.domain.usecase

import com.gsoft.conexachallenge.data.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTotalPrice  @Inject constructor (
    private val apiRepository: ProductRepository
) {
    suspend operator fun invoke() : Flow<Double?> {
        return  apiRepository.getTotalPrice()
    }
}