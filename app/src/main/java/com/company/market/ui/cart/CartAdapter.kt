package com.company.market.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.market.data.Order
import com.company.market.databinding.CartItemBinding

class CartAdapter(
    private val clickListener: CartFragment.ClickHandler
) : ListAdapter<Order, CartAdapter.CartViewHolder>(CALLBACK) {

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order) {
            binding.apply {
                textViewName.text = order.product_name
                textViewQuantity.text = String.format("%.2f kg", order.quantity)
                textViewPrice.text = String.format("₹ %d", order.price)
                addButton.setOnClickListener { clickListener.add(adapterPosition) }
                removeButton.setOnClickListener { clickListener.remove(adapterPosition) }
            }
        }
    }

    override fun submitList(list: MutableList<Order>?) {
        super.submitList(
            if (list.isNullOrEmpty()) {
                listOf()
            } else {
                list.toList()
            }
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(CartItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) =
        holder.bind(getItem(position))


    private object CALLBACK : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(
            oldItem: Order,
            newItem: Order
        ): Boolean {
            return oldItem.product_id == newItem.product_id
        }

        override fun areContentsTheSame(
            oldItem: Order,
            newItem: Order
        ): Boolean {
            return oldItem.quantity == newItem.quantity
        }
    }

}