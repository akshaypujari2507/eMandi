package com.emandi.ui.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.emandi.R
import com.emandi.data.local.entities.Order
import com.emandi.di.Injection
import com.emandi.ui.adapter.OrderDetailsAdapter
import com.emandi.ui.viewmodel.OrderDetailsActivityViewModel
import com.emandi.utils.Util
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrderDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: OrderDetailsActivityViewModel
    private lateinit var mGridLayoutManager: GridLayoutManager

    private val GRID_COLUMNS_PORTRAIT = 1
    private val GRID_COLUMNS_LANDSCAPE = 2
    var discountAmountWithDecimal: Double? = null
    var amountWithDecimal: Double? = null

    lateinit var mAdapter: OrderDetailsAdapter
    var orderID: String? = null
    var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)


        orderID = intent.getStringExtra(Util.ORDERID_KEY)
        title = intent.getStringExtra(Util.TITLE_KEY)

        viewModel =
            ViewModelProviders.of(this, Injection.provideOrderDetailsActivityViewModelFactory(this))
                .get(OrderDetailsActivityViewModel::class.java)

        initRecyclerView()

        GlobalScope.launch(Dispatchers.Main) {
            fetchCartProduct()
        }

    }

    private fun initRecyclerView() {

        tv_title.setText("$title")

        iv_back.setOnClickListener() {
            onBackPressed()
        }

        configureRecyclerAdapter(resources.configuration.orientation)

        mAdapter = OrderDetailsAdapter()
        recycler_view.adapter = mAdapter
    }

    suspend fun fetchCartProduct() {

//        viewModel.getCartProducts().

        viewModel.getOrderSumDiscountAmount(orderID!!)?.observe(this, Observer<Double> {
            if (it != null) {
                discountAmountWithDecimal = Math.round(it * 100.0) / 100.0
                tv_discount_amount.setText("₹  $discountAmountWithDecimal")
            } else {
                tv_discount_amount.setText(resources.getString(R.string.rupee_0))
            }
        })

        viewModel.getOrders(orderID!!).observe(this, Observer<List<Order>> {
            setUpAdapter(it)
            amountWithDecimal = Math.round(it[0].product_amount!! * 100.0) / 100.0
            tv_final_amount.setText("${it[0].totalOrderAmount}")
            setTotalItemAmount(amountWithDecimal!!, discountAmountWithDecimal!!)
        })

    }

    private fun setTotalItemAmount(totalAmount: Double, totalDiscountAmount: Double) {
        tv_item_total_amount.setText("₹  ${totalAmount + Math.abs(totalDiscountAmount)}")
    }

    private fun setUpAdapter(orders: List<Order>) {
        if (orders.size > 0) {
            mAdapter.orders = orders
            recycler_view.visibility = View.VISIBLE
            tv_no_records.visibility = View.GONE
            recycler_view.setItemViewCacheSize(orders.size);

            cv_details.visibility = View.VISIBLE

        } else {
            recycler_view.visibility = View.GONE
            tv_no_records.visibility = View.VISIBLE

            cv_details.visibility = View.GONE

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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}