package com.emandi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emandi.R
import com.emandi.data.local.entities.Cart
import com.emandi.di.Injection
import com.emandi.ui.interfaces.OnCartProductClicked
import com.emandi.ui.view.viewholder.CartProductViewHolder


class CartProductAdapter(private val listener: OnCartProductClicked) :
    RecyclerView.Adapter<CartProductViewHolder>() {

    var carts: List<Cart>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = carts?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_list_item, parent, false)
        val repo = Injection.provideRepository(parent.context)
        return CartProductViewHolder(
            view, listener, parent.context, repo
        )
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        val cart: Cart? = carts?.get(position)!!
        if (cart != null) {
            val viewHolder = holder as CartProductViewHolder
            viewHolder.bindNowShowingData(cart)
//            viewHolder.myCustomEditTextListener.updatePosition(cart)
        } else {
            notifyItemRemoved(position)
        }
    }

}
