package com.company.market.ui.market

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.company.market.R
import com.company.market.data.Product

class ProductAdapter(list: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.CompactViewHolder>() {
    private val productList: List<Product> = list

    inner class CompactViewHolder(private val item: View) :
        RecyclerView.ViewHolder(item) {
        fun bind(product: Product) {
            item.findViewById<TextView>(R.id.textView_Price).text =
                String.format("₹%d", product.price)
            item.findViewById<TextView>(R.id.textView_title).text = product.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CompactViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_compact_product, parent, false)
    )

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: CompactViewHolder, position: Int) =
        holder.bind(productList[position])
}