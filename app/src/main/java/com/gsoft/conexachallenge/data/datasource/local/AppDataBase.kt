package com.gsoft.conexachallenge.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gsoft.conexachallenge.data.datasource.local.entities.ProductDB


@Database(entities = [
    ProductDB::class],
    version = 2, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {
    abstract fun dao(): ProductDao
}