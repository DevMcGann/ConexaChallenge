package com.gsoft.conexachallenge.data.datasource.remote

import com.gsoft.conexachallenge.data.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductResponse>>
}