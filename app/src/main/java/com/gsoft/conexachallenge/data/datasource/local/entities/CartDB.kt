package com.gsoft.conexachallenge.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartDB (
    @PrimaryKey
    val id : Int,
    val products : List<ProductDB>,
    val total : Double
)