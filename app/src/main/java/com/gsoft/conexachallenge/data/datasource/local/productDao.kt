package com.gsoft.conexachallenge.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

@Dao
interface ProductDao {
    @Transaction
    fun upsert(productDB: ProductDB) {
        if (getById(productDB.id) != null) {
            update(productDB)
        } else {
            insert(productDB)
        }
    }

    @Transaction
    fun upsert(list: List<ProductDB>) {
        for (product in list) {
            upsert(product)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productDB: ProductDB)

    @Update
    fun update(productDB: ProductDB)

    @Query("SELECT * FROM products WHERE Id = :id LIMIT 1")
    fun getById(id: Int): ProductDB?

    @Query("DELETE FROM products")
    fun clearProducts()

    @Delete
    fun delete(productDB: ProductDB)

    @Query("""
        SELECT * FROM products
        """)
    fun getProducts(): Flow<List<ProductDB>>

    @Query("""
        SELECT SUM(quantity * price) FROM products
        """)
    fun getTotalPrice(): Flow<Double>


}