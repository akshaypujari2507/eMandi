package com.emandi.ui.interfaces

import com.emandi.data.local.entities.Order

interface OnOrderClicked {
    fun onOrderClicked(order: Order, title: String)
}