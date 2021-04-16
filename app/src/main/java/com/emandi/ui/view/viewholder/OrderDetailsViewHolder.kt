package com.emandi.ui.view.viewholder

import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emandi.R
import com.emandi.data.local.entities.Order


class OrderDetailsViewHolder(
    itemView: View?
) : RecyclerView.ViewHolder(itemView!!) {

    var tv_product_name: TextView
    var tv_product_price: TextView
    var tv_product_mrp: TextView
    var tv_quantity: TextView
    var tv_product_amount: TextView

    private var mOrder: Order? = null


    init {
        tv_product_name = itemView!!.findViewById(R.id.tv_product_name)
        tv_product_price = itemView!!.findViewById(R.id.tv_product_price)
        tv_product_mrp = itemView!!.findViewById(R.id.tv_product_mrp)
        tv_quantity = itemView!!.findViewById(R.id.tv_quantity)
        tv_product_amount = itemView!!.findViewById(R.id.tv_product_amount)

    }

    fun bindNowShowingData(mOrder: Order?) {
        if (mOrder == null) {
            return
        } else {

            this.mOrder = mOrder

            tv_product_name.setText("${mOrder.product_name}")
            tv_product_price.setText("₹ ${mOrder.price}")
            tv_product_mrp.setText("₹ ${mOrder.mrp}")
            tv_product_mrp.setPaintFlags(tv_product_mrp.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

            tv_quantity.setText("${mOrder.product_quantity}")
            tv_product_amount.setText("${mOrder.product_amount}")
        }
    }


}