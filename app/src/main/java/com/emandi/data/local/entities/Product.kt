package com.emandi.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Product", indices = [Index(value = ["product_id"], unique = true)])
class Product(
    val mrp: Double,
    val price: Double,
    val product_brand_id: Int,
    val product_code: String,
    val product_id: Long,
    val product_name: String,
    val product_weight: Int,
    val product_weight_unit: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    override fun equals(other: Any?): Boolean {
        return id == other
    }

    override fun hashCode(): Int {
        return id!!
    }

    override fun toString(): String {
        return "Product(mrp=$mrp, price=$price, product_brand_id=$product_brand_id, product_code=$product_code, product_id=$product_id, " +
                "product_name=$product_name, product_weight=$product_weight, product_weight_unit=$product_weight_unit)"
    }
}