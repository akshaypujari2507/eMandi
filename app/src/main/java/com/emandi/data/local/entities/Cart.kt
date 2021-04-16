package com.emandi.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Cart",
    indices = [Index(
        value = ["product_id", "product_name", "isAddToCart", "date_time"],
        unique = true
    )]
)
class Cart(
    val mrp: Double? = null,
    val price: Double? = null,
    val product_brand_id: Int? = null,
    val product_code: String? = null,
    val product_id: Long? = null,
    val product_name: String? = null,
    val product_weight: Int? = null,
    val product_weight_unit: String? = null,
    var product_quantity: Int? = null,
    var product_amount: Double? = null,
    var isAddToCart: Boolean = false,
    var discountAmount: Double? = null,
    var date_time: String? = null
) {
    @PrimaryKey
    var id: Int? = null

    override fun equals(other: Any?): Boolean {
        return id == other
    }

    override fun hashCode(): Int {
        return id!!
    }

    override fun toString(): String {
        return "Product(mrp=$mrp, price=$price, product_brand_id=$product_brand_id, product_code=$product_code, product_id=$product_id, " +
                "product_name=$product_name, product_weight=$product_weight, product_weight_unit=$product_weight_unit, product_quantity=$product_quantity," +
                "isAddToCart = $isAddToCart)"
    }
}