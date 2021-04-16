package com.emandi.ui.view.viewholder

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emandi.R
import com.emandi.data.local.entities.Cart
import com.emandi.data.repository.Repository
import com.emandi.ui.interfaces.OnCartProductClicked


class CartProductViewHolder(
    itemView: View?,
    val listener: OnCartProductClicked,
    val context: Context,
    val repo: Repository
) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {

    var tv_product_name: TextView
    var tv_product_price: TextView
    var tv_product_mrp: TextView
    var et_quantity: EditText
    var iv_delete: ImageView
    private var mCart: Cart? = null


    init {
        tv_product_name = itemView!!.findViewById(R.id.tv_product_name)
        tv_product_price = itemView!!.findViewById(R.id.tv_product_price)
        tv_product_mrp = itemView!!.findViewById(R.id.tv_product_mrp)
        et_quantity = itemView!!.findViewById(R.id.et_quantity)
        iv_delete = itemView!!.findViewById(R.id.iv_delete)

//        itemView.setOnClickListener(this)
        iv_delete.setOnClickListener(this)

    }

    fun bindNowShowingData(mCart: Cart?) {
        if (mCart == null) {
            return
        } else {

            this.mCart = mCart

            tv_product_name.setText("${mCart.product_name}")
            tv_product_price.setText("₹ ${mCart.price}")
            tv_product_mrp.setText("₹ ${mCart.mrp}")
            tv_product_mrp.setPaintFlags(tv_product_mrp.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

            et_quantity.setText("${mCart.product_quantity}")
        }
    }

    override fun onClick(p0: View?) {
        val position: Int = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            listener.onItemClicked(mCart!!)
        }
    }

}