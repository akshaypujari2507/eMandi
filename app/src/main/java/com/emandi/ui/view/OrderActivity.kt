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
import com.emandi.data.local.entities.Order
import com.emandi.di.Injection
import com.emandi.ui.adapter.OrderProductAdapter
import com.emandi.ui.interfaces.OnOrderClicked
import com.emandi.ui.viewmodel.OrderActivityViewModel
import com.emandi.utils.Util
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrderActivity : AppCompatActivity(), OnOrderClicked {

    private lateinit var viewModel: OrderActivityViewModel
    private lateinit var mGridLayoutManager: GridLayoutManager

    private val GRID_COLUMNS_PORTRAIT = 1
    private val GRID_COLUMNS_LANDSCAPE = 2

    lateinit var mAdapter: OrderProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        viewModel =
            ViewModelProviders.of(this, Injection.provideOrderActivityViewModelFactory(this))
                .get(OrderActivityViewModel::class.java)

        initRecyclerView()

        GlobalScope.launch(Dispatchers.Main) {
            fetchOrderProduct()
        }

    }

    fun initRecyclerView() {

        iv_back.setOnClickListener() {
            onBackPressed()
        }

        configureRecyclerAdapter(resources.configuration.orientation)

        mAdapter = OrderProductAdapter(this@OrderActivity)
        recycler_view.adapter = mAdapter

    }

    suspend fun fetchOrderProduct() {

        viewModel.getOrders().observe(this, Observer<List<Order>> {
            setUpAdapter(it)
        })

    }

    private fun setUpAdapter(orders: List<Order>) {
        if (orders.size > 0) {
            mAdapter.orders = orders
            recycler_view.visibility = View.VISIBLE
            tv_no_records.visibility = View.GONE
            recycler_view.setItemViewCacheSize(orders.size);
        } else {
            recycler_view.visibility = View.GONE
            tv_no_records.visibility = View.VISIBLE
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

    override fun onOrderClicked(order: Order, title: String) {
        val intent = Intent(this, OrderDetailsActivity::class.java)
        intent.putExtra(Util.ORDERID_KEY, order.orderID)
        intent.putExtra(Util.TITLE_KEY, title)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}