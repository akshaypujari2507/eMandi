package com.emandi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emandi.R
import com.emandi.data.local.entities.Order
import com.emandi.di.Injection
import com.emandi.ui.interfaces.OnOrderClicked
import com.emandi.ui.view.viewholder.OrderProductViewHolder


class OrderProductAdapter(private val listener: OnOrderClicked) :
    RecyclerView.Adapter<OrderProductViewHolder>() {

    var orders: List<Order>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = orders?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_list_item, parent, false)
        val repo = Injection.provideRepository(parent.context)
        return OrderProductViewHolder(
            view, listener
        )
    }

    override fun onBindViewHolder(holder: OrderProductViewHolder, position: Int) {
        val order: Order? = orders?.get(position)!!
        if (orders != null) {
            val viewHolder = holder as OrderProductViewHolder
            viewHolder.bindNowShowingData(order, orders!!.size, position)
        } else {
            notifyItemRemoved(position)
        }
    }

}
