package com.gsoft.conexachallenge.domain.model

import com.gsoft.conexachallenge.data.model.Rating

data class Product(
    val id : Int,
    val title : String,
    val price : Double,
    val description : String,
    val category : String,
    val image : String,
    val rating : Rating
)

