package com.emandi.ui.interfaces

import com.emandi.data.local.entities.Product

interface OnMainProductClicked {
    fun onItemClicked(product: Product)
}