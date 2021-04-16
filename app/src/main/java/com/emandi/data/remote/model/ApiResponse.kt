package com.emandi.data.remote.model

import com.emandi.data.local.entities.Product

data class ApiResponse(
    val `data`: List<Product>,
    val msg: String,
    val paginate: Paginate,
    val status: Int
)