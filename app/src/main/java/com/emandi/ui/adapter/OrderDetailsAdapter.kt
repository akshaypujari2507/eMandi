package com.emandi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emandi.R
import com.emandi.data.local.entities.Order
import com.emandi.di.Injection
import com.emandi.ui.view.viewholder.OrderDetailsViewHolder


class OrderDetailsAdapter :
    RecyclerView.Adapter<OrderDetailsViewHolder>() {

    var orders: List<Order>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = orders?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_details_list_item, parent, false)
        val repo = Injection.provideRepository(parent.context)
        return OrderDetailsViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        val order: Order? = orders?.get(position)!!
        if (order != null) {
            val viewHolder = holder as OrderDetailsViewHolder
            viewHolder.bindNowShowingData(order)
        } else {
            notifyItemRemoved(position)
        }
    }

}
