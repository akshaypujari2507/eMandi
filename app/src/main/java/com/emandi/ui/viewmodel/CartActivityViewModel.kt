package com.emandi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.emandi.data.local.entities.Cart
import com.emandi.data.local.entities.Order
import com.emandi.data.repository.Repository

class CartActivityViewModel(private val repo: Repository) : ViewModel() {

    var cartProducts: LiveData<List<Cart>>? = null

    var sumQuantity: LiveData<Long>? = null
    var sumAmount: LiveData<Double>? = null
    var sumDiscountAmount: LiveData<Double>? = null


    suspend fun getCartProducts(): LiveData<List<Cart>> {
        if (cartProducts == null) {
            try {
                cartProducts = repo.getCartProducts()
            } catch (e: Exception) {
                println(e)
            }
        }
        return cartProducts!!
    }

    suspend fun getSumQuantity(flag: Int): LiveData<Long> {
        if (sumQuantity == null) {
            try {
                sumQuantity = repo.getCartSumQuantity(flag)
            } catch (e: Exception) {
                println(e)
            }
        }
        return sumQuantity!!
    }

    suspend fun getSumAmount(flag: Int): LiveData<Double> {
        if (sumAmount == null) {
            try {
                sumAmount = repo.getCartSumAmount(flag)
            } catch (e: Exception) {
                println(e)
            }
        }
        return sumAmount!!
    }

    suspend fun getCartSumDiscountAmount(flag: Int): LiveData<Double> {
        if (sumDiscountAmount == null) {
            try {
                sumDiscountAmount = repo.getCartSumDiscountAmount(flag)
            } catch (e: Exception) {
                println(e)
            }
        }
        return sumDiscountAmount!!
    }

    fun deleteCart(cart: Cart) {
        try {
            repo.deleteCart(cart)
        } catch (e: Exception) {
            println(e)
        }
    }

    fun deleteCartProducts(flag: Int) {
        try {
            repo.deleteCartProducts(flag)
        } catch (e: Exception) {
            println(e)
        }
    }

    fun insertOrders(orders: List<Order>) {
        try {
            repo.insertOrders(orders)
        } catch (e: Exception) {
            println(e)
        }
    }


}