package com.emandi.ui.interfaces

import com.emandi.data.local.entities.Cart

interface OnCartProductClicked {
    fun onItemClicked(cart: Cart)
}