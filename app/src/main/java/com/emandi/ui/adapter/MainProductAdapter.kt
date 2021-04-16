package com.emandi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emandi.R
import com.emandi.data.local.entities.Product
import com.emandi.di.Injection
import com.emandi.ui.interfaces.OnMainProductClicked
import com.emandi.ui.view.viewholder.MainProductViewHolder
import com.emandi.ui.view.viewholder.MainProductViewHolder.MyCustomEditTextListener
import java.text.SimpleDateFormat
import java.util.*


class MainProductAdapter(private val listener: OnMainProductClicked) :
    PagedListAdapter<Product, MainProductViewHolder>(ProductDiffCallback()) {
//    RecyclerView.Adapter<MainProductViewHolder>() {

    var products: List<Product>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = products?.size ?: 0

    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainProductViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_list_item, parent, false)
        val repo = Injection.provideRepository(parent.context)
        return MainProductViewHolder(
            view, listener, parent.context, repo,
            MyCustomEditTextListener()
        )
    }

    override fun onBindViewHolder(holder: MainProductViewHolder, position: Int) {
        val product: Product? = products?.get(position)!!
        if (product != null) {
            val viewHolder = holder as MainProductViewHolder
            viewHolder.bindNowShowingData(product)
            viewHolder.myCustomEditTextListener.updatePosition(product, currentDate)
        } else {
            notifyItemRemoved(position)
        }
    }

    class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
}
