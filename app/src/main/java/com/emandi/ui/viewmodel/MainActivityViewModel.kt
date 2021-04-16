package com.emandi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.emandi.data.local.entities.Product
import com.emandi.data.remote.model.ApiResponse
import com.emandi.data.repository.Repository

class MainActivityViewModel(private val repo: Repository) : ViewModel() {

    var remoteRecords: LiveData<ApiResponse>? = null
    var products: DataSource.Factory<Int, Product>? = null

    var sumQuantity: LiveData<Long>? = null
    var sumAmount: LiveData<Double>? = null

    fun getRemoteRecord(): LiveData<ApiResponse> {
        if (remoteRecords == null) {
            remoteRecords = repo.getRemoteRecord()
        }
        return remoteRecords!!
    }

    lateinit var personsLiveData: LiveData<PagedList<Product>>

    suspend fun getProducts(): LiveData<PagedList<Product>> {
        if (products == null) {
            try {
                products = repo.getProducts()
            } catch (e: Exception) {
                println(e)
            }
        }

//        val factory = products
        val pagedListBuilder: LivePagedListBuilder<Int, Product> = LivePagedListBuilder<Int, Product>(products!!,
            100)
        personsLiveData = pagedListBuilder.build()

        return personsLiveData!!
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

    fun deleteCartProducts(flag: Int) {
        try {
            repo.deleteCartProducts(flag)
        } catch (e: Exception) {
            println(e)
        }
    }

    fun updateCartProductStatus() {
        try {
            repo.updateCartProductStatus()
        } catch (e: Exception) {
            println(e)
        }
    }


}