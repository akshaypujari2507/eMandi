package com.emandi.ui.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.emandi.R
import com.emandi.data.local.entities.Cart
import com.emandi.data.local.entities.Order
import com.emandi.di.Injection
import com.emandi.ui.adapter.CartProductAdapter
import com.emandi.ui.interfaces.OnCartProductClicked
import com.emandi.ui.viewmodel.CartActivityViewModel
import com.emandi.utils.Util
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CartActivity : AppCompatActivity(), OnCartProductClicked {

    private lateinit var viewModel: CartActivityViewModel
    private lateinit var mGridLayoutManager: GridLayoutManager

    private val GRID_COLUMNS_PORTRAIT = 1
    private val GRID_COLUMNS_LANDSCAPE = 2
    var amountWithDecimal: Double? = null
    var discountAmountWithDecimal: Double? = null

    lateinit var mAdapter: CartProductAdapter

    var carts: List<Cart>? = null
    var orders: MutableList<Order> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        viewModel = ViewModelProviders.of(this, Injection.provideCartActivityViewModelFactory(this))
            .get(CartActivityViewModel::class.java)

        initRecyclerView()

        GlobalScope.launch(Dispatchers.Main) {
            fetchCartProduct()
        }
    }

    suspend fun fetchCartProduct() {

//        viewModel.getCartProducts().

        viewModel.getCartProducts().observe(this, Observer<List<Cart>> {
            carts = it
            setUpAdapter(it)
        })

        viewModel.getSumQuantity(1)?.observe(this, Observer<Long> {
            if (it != null) {
                tv_total_items.setText("$it ITEMS")
            } else {
                tv_total_items.setText("0 ITEMS")

                tv_item_total_amount.setText(resources.getString(R.string.rupee_0))

            }
        })

        viewModel.getCartSumDiscountAmount(1)?.observe(this, Observer<Double> {
            if (it != null) {
                discountAmountWithDecimal = Math.round(it * 100.0) / 100.0
                tv_discount_amount.setText("₹  $discountAmountWithDecimal")

            } else {
                tv_discount_amount.setText(resources.getString(R.string.rupee_0))
            }
        })

        viewModel.getSumAmount(1)?.observe(this, Observer<Double> {
            if (it != null) {
                amountWithDecimal = Math.round(it * 100.0) / 100.0
                tv_total_amount.setText("₹  $amountWithDecimal")
                tv_final_amount.setText("₹  $amountWithDecimal")

                setTotalItemAmount(amountWithDecimal!!, discountAmountWithDecimal!!)
            } else {
                tv_total_amount.setText(resources.getString(R.string.rupee_0))

                tv_item_total_amount.setText(resources.getString(R.string.rupee_0))
            }
        })


    }

    private fun setTotalItemAmount(totalAmount: Double, totalDiscountAmount: Double) {
        tv_item_total_amount.setText("₹  ${totalAmount + Math.abs(totalDiscountAmount)}")
    }

    fun Cart.toOrder() = Order(
        mrp,
        price,
        product_brand_id,
        product_code,
        product_id,
        product_name,
        product_weight,
        product_weight_unit,
        product_quantity,
        product_amount,
        isAddToCart,
        discountAmount,
        date_time
    )

    private fun initRecyclerView() {

        cl_bottom.setOnClickListener() {
            //make order
            makeOrder()
        }

        tv_clear.setOnClickListener() {
            viewModel.deleteCartProducts(1)
        }

        iv_back.setOnClickListener() {
            onBackPressed()
        }

        configureRecyclerAdapter(resources.configuration.orientation)

        mAdapter = CartProductAdapter(this@CartActivity)
        recycler_view.adapter = mAdapter
    }

    fun makeOrder() {
        val sdf = SimpleDateFormat("dd MMM, yyyy hh:mm:ss a")
        val currentDate = sdf.format(Date())
        val orderID: String = "OID${System.currentTimeMillis()}"

        for (cart in carts!!) {
            val order: Order = cart.toOrder()
            order.date_time = currentDate
            order.totalOrderAmount = amountWithDecimal
            order.orderID = orderID
            orders.add(order)
        }

        // insert into order table carts
        viewModel.insertOrders(orders)

        // call order screen (Screen 3)
        val intent = Intent(this, OrderCompleteActivity::class.java)
        startActivity(intent)

        // delete cart table
        viewModel.deleteCartProducts(1)

    }

    private fun setUpAdapter(carts: List<Cart>) {
        if (carts.size > 0) {
            mAdapter.carts = carts
            recycler_view.visibility = View.VISIBLE
            tv_no_records.visibility = View.GONE
            recycler_view.setItemViewCacheSize(carts.size);

            cv_details.visibility = View.VISIBLE
            cv_bottom.visibility = View.VISIBLE

        } else {
            recycler_view.visibility = View.GONE
            tv_no_records.visibility = View.VISIBLE

            cv_details.visibility = View.GONE
            cv_bottom.visibility = View.GONE

        }


    }

    private fun configureRecyclerAdapter(orientation: Int) {
        val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
        mGridLayoutManager = GridLayoutManager(
            this,
            if (isPortrait) GRID_COLUMNS_PORTRAIT else GRID_COLUMNS_LANDSCAPE
        )
        recycler_view.setLayoutManager(mGridLayoutManager)
    }

    override fun onItemClicked(cart: Cart) {
        //delete cart
        viewModel.deleteCart(cart)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(Util.SCREEN_KEY, "cart_screen")
        startActivity(intent)

    }

}