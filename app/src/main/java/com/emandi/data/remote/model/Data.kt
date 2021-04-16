package com.emandi.data.remote.model

data class Data(
    val mrp: String,
    val price: String,
    val product_brand_id: Int,
    val product_code: String,
    val product_id: Long,
    val product_name: String,
    val product_weight: Int,
    val product_weight_unit: String
)