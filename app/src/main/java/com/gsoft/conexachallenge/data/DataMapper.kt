package com.gsoft.conexachallenge.data

import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import com.gsoft.conexachallenge.data.model.ProductResponse
import com.gsoft.conexachallenge.domain.model.Product

object DataMapper {
    fun apiToDomain(productResponse: ProductResponse) : Product{
        return Product(
            id = productResponse.id,
            title = productResponse.title,
            price = productResponse.price,
            description = productResponse.description,
            category = productResponse.category,
            image = productResponse.image,
            rating = productResponse.rating
        )
    }

    fun domainToDB(product: Product) : ProductDB{
        return ProductDB(
            id = product.id,
            title = product.title,
            price = product.price,
            description = product.description,
            category = product.category,
            image = product.image
        )
    }


    fun ApiToDomain(list: List<ProductResponse>) =
        list.map { DataMapper.apiToDomain(it) }

    fun DomainToDB(list: List<Product>) =
        list.map { DataMapper.domainToDB(it) }
}