package com.emandi.ui.view.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emandi.R
import com.emandi.data.local.entities.Order
import com.emandi.ui.interfaces.OnOrderClicked


class OrderProductViewHolder(
    itemView: View?,
    val listener: OnOrderClicked
) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {

    val tv_order_no: TextView
    val tv_date_time: TextView
    val tv_total_amount: TextView

    private var mOrder: Order? = null


    init {
        tv_order_no = itemView!!.findViewById(R.id.tv_order_no)
        tv_date_time = itemView!!.findViewById(R.id.tv_date_time)
        tv_total_amount = itemView!!.findViewById(R.id.tv_total_amount)

        itemView.setOnClickListener(this)
    }

    fun bindNowShowingData(mOrder: Order?, orderSize: Int, position: Int) {
        if (mOrder == null) {
            return
        } else {

            this.mOrder = mOrder

            tv_order_no.setText("Order #${orderSize - position}")
            tv_date_time.setText("${mOrder.date_time}")
            tv_total_amount.setText("₹ ${mOrder.totalOrderAmount}")

        }
    }

    override fun onClick(p0: View?) {
        val position: Int = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            listener.onOrderClicked(mOrder!!, tv_order_no.text.toString())
        }
    }

}