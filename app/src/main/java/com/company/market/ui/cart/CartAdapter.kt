package com.company.market.ui.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.market.data.Order
import com.company.market.data.Product
import com.company.market.databinding.CartItemBinding

class CartAdapter(
    private val clickListener: CartFragment.ClickHandler,
    private val orderList: List<Order>?
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(CartItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return orderList?.size ?: 0
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        orderList?.get(position)?.let { holder.bind(it) }
        }

    inner class CartViewHolder( private val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(order: Order){
            binding.apply {
                textViewName.text = order.product_name
                textViewQuantity.text = String.format("%d kg", order.price)
                textViewPrice.text = String.format("₹ %d", order.quantity * order.price)
                addButton.setOnClickListener { clickListener.add(adapterPosition) }
                removeButton.setOnClickListener { clickListener.remove(adapterPosition) }
            }
        }
    }
}
