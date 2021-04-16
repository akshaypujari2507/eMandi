package com.emandi.ui.view.viewholder

import android.content.Context
import android.graphics.Paint
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.emandi.R
import com.emandi.data.local.entities.Cart
import com.emandi.data.local.entities.Product
import com.emandi.data.repository.Repository
import com.emandi.di.Injection.repo
import com.emandi.ui.interfaces.OnMainProductClicked
import com.emandi.utils.Util


class MainProductViewHolder(
    itemView: View?,
    val listener: OnMainProductClicked,
    val context: Context,
    val repo: Repository,
    var myCustomEditTextListener: MyCustomEditTextListener
) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {

    var tv_product_name: TextView
    var tv_product_price: TextView
    var tv_product_mrp: TextView
    var et_quantity: EditText
    private var mProduct: Product? = null


    init {
        tv_product_name = itemView!!.findViewById(R.id.tv_product_name)
        tv_product_price = itemView!!.findViewById(R.id.tv_product_price)
        tv_product_mrp = itemView!!.findViewById(R.id.tv_product_mrp)
        et_quantity = itemView!!.findViewById(R.id.et_quantity)

//        itemView.setOnClickListener(this)

    }

    fun bindNowShowingData(mProduct: Product?) {
        if (mProduct == null) {
            return
        } else {

            this.mProduct = mProduct

            tv_product_name.setText("${mProduct.product_name}")
            tv_product_price.setText("₹ ${mProduct.price}")
            tv_product_mrp.setText("₹ ${mProduct.mrp}")
//            et_quantity.setText("")
            tv_product_mrp.setPaintFlags(tv_product_mrp.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

//            et_quantity.filters = arrayOf(MinMaxFilter(1, 100))
//            et_quantity.setFilters(arrayOf<InputFilter>(MinMaxFilter(1, 99)))

            myCustomEditTextListener.et_quantity = et_quantity
            myCustomEditTextListener.context = context
            et_quantity.addTextChangedListener(myCustomEditTextListener);

        }
    }

    class MyCustomEditTextListener : TextWatcher {
        var mProduct: Product? = null
        var mCurrentDate: String? = null
        var context: Context? = null
        var et_quantity: EditText? = null

        fun updatePosition(product: Product?, currentDate: String?) {
            this.mProduct = product
            this.mCurrentDate = currentDate
        }

        override fun beforeTextChanged(
            charSequence: CharSequence,
            i: Int,
            i2: Int,
            i3: Int
        ) {
            // no op
        }

        override fun onTextChanged(
            s: CharSequence,
            i: Int,
            i2: Int,
            i3: Int
        ) {
            ////////////////////////////////////////
            var cart: Cart = Cart(
                mProduct?.mrp,
                mProduct?.price,
                mProduct?.product_brand_id,
                mProduct?.product_code,
                mProduct?.product_id,
                mProduct?.product_name,
                mProduct?.product_weight,
                mProduct?.product_weight_unit,
                isAddToCart = false
            )

            if (s.length != 0) {

                val et_number = s.toString().toInt()
                if (et_number == 0) {
                    et_quantity?.setText("")
                    return
                }

                try {


//                        insert in cart table
                    val quantity = s.toString().toInt()
                    val amount = (s.toString().toDouble()) * cart.price!!
                    val discountAmount = (s.toString().toDouble()) * (cart.price!! - cart.mrp!!)

                    val amountWithDecimal = Math.round(amount * 100.0) / 100.0
                    val discountAmountWithDecimal = Math.round(discountAmount * 100.0) / 100.0

                    println(
                        " Product Name ${cart.product_name}: Quantity = ${quantity}, Amount = ${amount}, DiscountAmount = ${discountAmount}, " +
                                "AmountWithDecimal = ${amountWithDecimal}, DiscountAmountWithDecimal = ${discountAmountWithDecimal}"
                    )
                    cart.product_quantity = s.toString().toInt()
                    cart.product_amount = amountWithDecimal
                    cart.discountAmount = discountAmountWithDecimal
                    cart.date_time = mCurrentDate

                    repo?.insertCart(cart)
                } catch (e: Exception) {
                    println(e)
                }
                if (Util.checksdkforbackground()) {
                    et_quantity?.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.et_background_yellow
                        )
                    );
                } else {
                    et_quantity?.setBackground(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.et_background_yellow
                        )
                    );
                }

            } else {
                if (Util.checksdkforbackground()) {
                    et_quantity?.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.et_background_normal
                        )
                    );
                } else {
                    et_quantity?.setBackground(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.et_background_normal
                        )
                    );
                }

//                        delete in cart table
                repo?.deleteCart(cart.product_id!!, 0)
            }

            ////////////////////////////////////////
        }

        override fun afterTextChanged(editable: Editable) {
            // no op
        }
    }

    class MinMaxFilter(val mIntMin: Int, val mIntMax: Int) : InputFilter {

        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence {
            try {
                val input = (dest.toString() + source.toString()).toInt()
                if (isInRange(mIntMin, mIntMax, input)) return ""
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            return ""
        }

        private fun isInRange(a: Int, b: Int, c: Int): Boolean {
            return if (b > a) c >= a && c <= b else c >= b && c <= a
        }
    }

    override fun onClick(p0: View?) {
        val position: Int = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            listener.onItemClicked(mProduct!!)
        }
    }

}