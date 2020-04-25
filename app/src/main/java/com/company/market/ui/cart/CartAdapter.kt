package com.company.market.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.market.data.Product
import com.company.market.databinding.CartItemBinding

class CartAdapter(
    private val clickListener: CartFragment.ClickHandler
) : ListAdapter<Pair<Product, Int>, CartAdapter.CartViewHolder>(CALLBACK) {

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pair: Pair<Product, Int>) {
            binding.apply {
                textViewName.text = pair.first.title
                textViewQuantity.text = String.format("%d kg", pair.second)
                textViewPrice.text = String.format("₹ %d", pair.second * pair.first.price)
                addButton.setOnClickListener { clickListener.add(adapterPosition) }
                removeButton.setOnClickListener { clickListener.remove(adapterPosition) }
            }
        }
    }

    override fun submitList(list: MutableList<Pair<Product, Int>>?) {
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


    private object CALLBACK : DiffUtil.ItemCallback<Pair<Product, Int>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Product, Int>,
            newItem: Pair<Product, Int>
        ): Boolean {
            return oldItem.first.id == newItem.first.id
        }

        override fun areContentsTheSame(
            oldItem: Pair<Product, Int>,
            newItem: Pair<Product, Int>
        ): Boolean {
            return oldItem.second == newItem.second
        }
    }

}