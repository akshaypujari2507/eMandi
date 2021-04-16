package com.emandi.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.emandi.data.local.db.AppDatabase
import com.emandi.data.local.entities.Cart
import com.emandi.data.local.entities.Order
import com.emandi.data.local.entities.Product
import com.emandi.data.remote.api.ApiService
import com.emandi.data.remote.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(val db: AppDatabase, val api: ApiService) {

    lateinit var products: DataSource.Factory<Int, Product>
    lateinit var cartProducts: LiveData<List<Cart>>
    lateinit var orders: LiveData<List<Order>>
    lateinit var order: LiveData<List<Order>>
    lateinit var sumQuantity: LiveData<Long>
    lateinit var sumAmount: LiveData<Double>
    lateinit var sumDiscountAmount: LiveData<Double>

    // network call
    fun getRemoteRecord(): LiveData<ApiResponse> {

        val remoteRecords: MutableLiveData<ApiResponse> = MutableLiveData<ApiResponse>()

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getResponse().execute().body()
            remoteRecords.postValue(response)
            insertRecords(response!!)
        }

        return remoteRecords
    }

    fun insertRecords(response: ApiResponse) {
        val responseData = response.data

        GlobalScope.launch(Dispatchers.IO) {
            db.productDao().insertProduct(responseData)
        }

    }

    // main activity
    suspend fun getProducts(): DataSource.Factory<Int, Product> {
        withContext(Dispatchers.IO) {
            try {
                products = db.productDao().getProducts()
            } catch (e: Exception) {
                println(e)
            }
        }
        return products
    }

    // cart activity list
    suspend fun getCartProducts(): LiveData<List<Cart>> {
        withContext(Dispatchers.IO) {
            try {
                cartProducts = db.cartDao().getCartProducts()
            } catch (e: Exception) {
                println(e)
            }
        }
        return cartProducts
    }

    suspend fun getCartSumQuantity(flag: Int): LiveData<Long> {
        withContext(Dispatchers.IO) {
            try {
                sumQuantity = db.cartDao().getCartSumQuantity(flag)
            } catch (e: Exception) {
                println(e)
            }
        }
        return sumQuantity
    }

    suspend fun getCartSumAmount(flag: Int): LiveData<Double> {
        withContext(Dispatchers.IO) {
            try {
                sumAmount = db.cartDao().getCartSumAmount(flag)
            } catch (e: Exception) {
                println(e)
            }
        }
        return sumAmount
    }

    suspend fun getCartSumDiscountAmount(flag: Int): LiveData<Double> {
        withContext(Dispatchers.IO) {
            try {
                sumDiscountAmount = db.cartDao().getCartSumDiscountAmount(flag)
            } catch (e: Exception) {
                println(e)
            }
        }
        return sumDiscountAmount
    }

    suspend fun getOrderSumDiscountAmount(orderID: String): LiveData<Double> {
        withContext(Dispatchers.IO) {
            try {
                sumDiscountAmount = db.orderDao().getOrderSumDiscountAmount(orderID)
            } catch (e: Exception) {
                println(e)
            }
        }
        return sumDiscountAmount
    }

    fun deleteCart(productID: Long, flag: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            db.cartDao().deleteCartProduct(productID, flag)
        }
    }

    fun deleteCartProducts(flag: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            db.cartDao().deleteCartProducts(flag)
        }
    }

    fun insertCart(cart: Cart) {
        GlobalScope.launch(Dispatchers.IO) {
            db.cartDao().insertProductInCart(cart)
        }
    }

    fun insertOrders(orders: List<Order>) {
        GlobalScope.launch(Dispatchers.IO) {
            db.orderDao().insertOrders(orders)
        }
    }

    fun deleteCart(cart: Cart) {
        GlobalScope.launch(Dispatchers.IO) {
            db.cartDao().deleteCart(cart)
        }
    }

    fun updateCartProductStatus() {
        GlobalScope.launch(Dispatchers.IO) {
            db.cartDao().updateCartProductStatus()
        }
    }

    fun updateCartProduct(cart: Cart) {
        GlobalScope.launch(Dispatchers.IO) {
            db.cartDao().updateCartProduct(cart)
        }
    }

    // order activity list
    suspend fun getOrders(): LiveData<List<Order>> {
        withContext(Dispatchers.IO) {
            try {
                orders = db.orderDao().getOrders()
            } catch (e: Exception) {
                println(e)
            }
        }
        return orders
    }

    // order details activity list
    suspend fun getOrders(orderID: String): LiveData<List<Order>> {
        withContext(Dispatchers.IO) {
            try {
                order = db.orderDao().getOrders(orderID)
            } catch (e: Exception) {
                println(e)
            }
        }
        return order
    }


}