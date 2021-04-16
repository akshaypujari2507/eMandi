package com.emandi.data.local.doa

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emandi.data.local.entities.Product


@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun getProducts(): DataSource.Factory<Int, Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: List<Product>)

}