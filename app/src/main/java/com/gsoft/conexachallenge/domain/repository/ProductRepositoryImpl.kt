package com.gsoft.conexachallenge.domain.repository

import com.gsoft.conexachallenge.data.DataMapper
import com.gsoft.conexachallenge.data.datasource.local.ProductDao
import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.data.datasource.remote.ProductApi
import com.gsoft.conexachallenge.data.repository.ProductRepository
import com.gsoft.conexachallenge.domain.model.Product
import com.gsoft.conexachallenge.util.NetworkUtils
import com.gsoft.conexachallenge.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val api : ProductApi,
    val dao : ProductDao,
    val networkUtils : NetworkUtils
):ProductRepository {

    override suspend fun getProducts(): Resource<List<Product>> {
        if (!networkUtils.isNetworkConnected()){
            return Resource.error(message = "No internet connection", data = null)
        }

        val data : MutableList<Product> = mutableListOf()
        return try {
            val response = api.getProducts()
            if (response.isSuccessful && response.body() != null){
                response.body()?.forEach {
                    data.add(DataMapper.apiToDomain(it))
                }
            }else{
                data.addAll(emptyList())
            }
            Resource.success(data)
        }catch (e : Exception){
            Resource.error(message = e.message.toString(), data = null)
        }
    }

    override suspend fun getCartProducts():Flow<List<ProductDB>> {
        return dao.getProducts()
    }

    override suspend fun addProductToCart(productDB: ProductDB) {
        val product = dao.getById(productDB.id)
        if (isProductInCart(productDB = productDB)){
            product?.let {
                dao.update(it.copy(quantity = it.quantity + 1))
            }
        }else{
            productDB.copy(quantity = 1).let {
                dao.insert(it)
            }
        }
    }

    override suspend fun removeProductFromCart(productDB: ProductDB) {
        if (isProductInCart(productDB) && productDB.quantity > 1) {
            restQuantity(productDB)
        }else{
            dao.delete(productDB)
        }

    }

    override suspend fun getTotalPrice(): Flow<Double?> {
        return dao.getTotalPrice()
    }

    override suspend fun clearCart() {
        dao.clearProducts()
    }



    private fun isProductInCart(productDB: ProductDB) : Boolean {
        val product = dao.getById(productDB.id)
        return product != null
    }

    private fun addQuantity(productDB: ProductDB) {
        dao.getById(productDB.id)?.copy(quantity = productDB.quantity + 1)?.let {
            dao.update(it)
        }
    }


    private fun restQuantity(productDB: ProductDB) {
        dao.getById(productDB.id)?.copy(quantity = productDB.quantity -1 )?.let {
            dao.update(it)
        }
    }
}