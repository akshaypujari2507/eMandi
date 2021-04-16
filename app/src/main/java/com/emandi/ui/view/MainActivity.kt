package com.emandi.ui.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.emandi.R
import com.emandi.data.local.entities.Product
import com.emandi.data.remote.model.ApiResponse
import com.emandi.di.Injection
import com.emandi.ui.adapter.MainProductAdapter
import com.emandi.ui.interfaces.OnMainProductClicked
import com.emandi.ui.viewmodel.MainActivityViewModel
import com.emandi.utils.Util
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnMainProductClicked {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mGridLayoutManager: GridLayoutManager

    private val GRID_COLUMNS_PORTRAIT = 1
    private val GRID_COLUMNS_LANDSCAPE = 2

    lateinit var mAdapter: MainProductAdapter
    var screen: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screen = intent.getStringExtra(Util.SCREEN_KEY).toString()

        viewModel = ViewModelProviders.of(this, Injection.provideMainActivityViewModelFactory(this))
            .get(MainActivityViewModel::class.java)

        initRecyclerView()

        GlobalScope.launch(Dispatchers.Main) {

            if (Util.isNetworkAvailable(this@MainActivity)) {

                if (TextUtils.isEmpty(screen) || screen.equals("null")) {
                    progressBar.visibility = View.VISIBLE
                    fetchRemoteRecords()
                } else {
                    fetchProduct()
                }
            } else {
                fetchProduct()
            }

        }

    }

    private fun initRecyclerView() {

        cl_bottom.setOnClickListener() {
            viewModel.updateCartProductStatus()

            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            finish()
        }

        tv_all_order.setOnClickListener() {
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }

        configureRecyclerAdapter(resources.configuration.orientation)

        mAdapter = MainProductAdapter(this@MainActivity)
        recycler_view.adapter = mAdapter
    }


    suspend fun fetchProduct() {

        viewModel.deleteCartProducts(0)


        viewModel.getProducts().observe(this, Observer {
//            if (it != null) mAdapter.products = it
            setUpAdapter(it)
        })
/*
        viewModel.getProducts()?.observe(this, Observer<List<Product>> {
            setUpAdapter(it)
        })
*/

        viewModel.getSumQuantity(0)?.observe(this, Observer<Long> {
            if (it != null) {
                tv_total_items.setText("$it ITEMS")
            } else {
                tv_total_items.setText("0 ITEMS")
            }
        })

        viewModel.getSumAmount(0)?.observe(this, Observer<Double> {
            if (it != null) {
                val amountWithDecimal = Math.round(it * 100.0) / 100.0
                tv_total_amount.setText("₹  $amountWithDecimal")
            } else {
                tv_total_amount.setText(resources.getString(R.string.rupee_0))
            }
        })

    }

    private fun setUpAdapter(products: List<Product>) {
        if (products.size > 0) {
            mAdapter.products = products
            recycler_view.visibility = View.VISIBLE
            cv_bottom.visibility = View.VISIBLE
            tv_no_records.visibility = View.GONE
            recycler_view.setItemViewCacheSize(products.size);

        } else {
            recycler_view.visibility = View.GONE
            cv_bottom.visibility = View.GONE
            tv_no_records.visibility = View.VISIBLE
        }


    }


    private suspend fun fetchRemoteRecords() {

        viewModel.getRemoteRecord()?.observe(this, Observer<ApiResponse> {
            progressBar.visibility = View.GONE
            GlobalScope.launch(Dispatchers.Main) {
                fetchProduct()
            }
        })
    }

    private fun configureRecyclerAdapter(orientation: Int) {
        val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
        mGridLayoutManager = GridLayoutManager(
            this,
            if (isPortrait) GRID_COLUMNS_PORTRAIT else GRID_COLUMNS_LANDSCAPE
        )
        recycler_view.setLayoutManager(mGridLayoutManager)
    }

    override fun onItemClicked(product: Product) {

    }


}