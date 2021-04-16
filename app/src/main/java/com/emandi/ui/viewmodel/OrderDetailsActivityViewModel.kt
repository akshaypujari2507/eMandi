package com.emandi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.emandi.data.local.entities.Order
import com.emandi.data.repository.Repository

class OrderDetailsActivityViewModel(private val repo: Repository) : ViewModel() {

    var productOrder: LiveData<List<Order>>? = null
    var sumDiscountAmount: LiveData<Double>? = null

    suspend fun getOrders(orderID: String): LiveData<List<Order>> {
        if (productOrder == null) {
            try {
                productOrder = repo.getOrders(orderID)
            } catch (e: Exception) {
                println(e)
            }
        }
        return productOrder!!
    }

    suspend fun getOrderSumDiscountAmount(orderID: String): LiveData<Double> {
        if (sumDiscountAmount == null) {
            try {
                sumDiscountAmount = repo.getOrderSumDiscountAmount(orderID)
            } catch (e: Exception) {
                println(e)
            }
        }
        return sumDiscountAmount!!
    }


}