package com.emandi.data.local.doa

import androidx.lifecycle.LiveData
import androidx.room.*
import com.emandi.data.local.entities.Cart


@Dao
interface CartDao {

    @Query("SELECT * FROM Cart")
    fun getCartProducts(): LiveData<List<Cart>>

    @Query("SELECT sum(product_quantity) FROM Cart where isAddToCart  = :flag")
    fun getCartSumQuantity(flag: Int): LiveData<Long>

    @Query("SELECT sum(product_amount) FROM Cart where isAddToCart  = :flag")
    fun getCartSumAmount(flag: Int): LiveData<Double>

    @Query("SELECT sum(discountAmount) FROM Cart where isAddToCart  = :flag")
    fun getCartSumDiscountAmount(flag: Int): LiveData<Double>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProductInCart(cart: Cart)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCartProduct(cart: Cart)

    @Query("update Cart set isAddToCart = 1")
    fun updateCartProductStatus()

    @Query("delete FROM Cart where product_id = :productID and isAddToCart  = :flag")
    fun deleteCartProduct(productID: Long, flag: Int)

    @Query("delete FROM Cart where isAddToCart  = :flag")
    fun deleteCartProducts(flag: Int)

    @Delete
    fun deleteCart(cart: Cart)

}