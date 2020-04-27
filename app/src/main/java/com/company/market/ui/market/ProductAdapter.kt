package com.company.market.ui.market

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.company.market.R
import com.company.market.data.Product


class ProductAdapter :
    androidx.recyclerview.widget.ListAdapter<Product, ProductAdapter.CompactViewHolder>(
        CALLBACK
    ) {
    inner class CompactViewHolder(private val item: View) :
        RecyclerView.ViewHolder(item) {
        fun bind(product: Product) {
            item.findViewById<TextView>(R.id.textView_Price).text =
                String.format("₹%.2f %s", product.price.toFloat() / product.quantity, product.unit)
            item.findViewById<TextView>(R.id.textView_title).text = product.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CompactViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_compact_product, parent, false)
    )

    override fun submitList(list: List<Product>?) {
        super.submitList(
            if (list.isNullOrEmpty()) {
                listOf()
            } else {
                list.toList()
            }
        )
    }

    override fun onBindViewHolder(holder: CompactViewHolder, position: Int) =
        holder.bind(getItem(position))

    private object CALLBACK : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.price == newItem.price ||
                    oldItem.inStock == newItem.inStock
        }
    }
}