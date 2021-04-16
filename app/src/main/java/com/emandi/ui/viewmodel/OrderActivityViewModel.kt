package com.emandi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.emandi.data.local.entities.Order
import com.emandi.data.repository.Repository

class OrderActivityViewModel(private val repo: Repository) : ViewModel() {

    var orders: LiveData<List<Order>>? = null

    suspend fun getOrders(): LiveData<List<Order>> {
        if (orders == null) {
            try {
                orders = repo.getOrders()
            } catch (e: Exception) {
                println(e)
            }
        }
        return orders!!
    }

}