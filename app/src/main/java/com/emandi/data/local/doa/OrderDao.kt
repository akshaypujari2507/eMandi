package com.emandi.data.local.doa

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emandi.data.local.entities.Order


@Dao
interface OrderDao {

    @Query("SELECT * FROM `Order` GROUP by date_time ORDER by id DESC")
    fun getOrders(): LiveData<List<Order>>

    @Query("SELECT * FROM `Order` where orderID = :orderID")
    fun getOrders(orderID: String): LiveData<List<Order>>

    @Query("SELECT sum(discountAmount) FROM `Order` where orderID  = :orderID")
    fun getOrderSumDiscountAmount(orderID: String): LiveData<Double>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrders(product: List<Order>)

}