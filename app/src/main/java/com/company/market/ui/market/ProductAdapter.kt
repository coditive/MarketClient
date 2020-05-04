package com.company.market.ui.market

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.company.market.R
import com.company.market.data.Product
import com.company.market.databinding.ItemCompactProductBinding


class ProductAdapter(private val clickHandler: MarketFragment.ClickHandler) :
    androidx.recyclerview.widget.ListAdapter<Product, ProductAdapter.CompactViewHolder>(
        CALLBACK
    ) {
    inner class CompactViewHolder(private val binding: ItemCompactProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                addRemoveButton.apply {
                    if (product.inStock) {
                        if (product.isInCart) {
                            setOnClickListener { clickHandler.removeFromCart(adapterPosition) }
                            setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.color_on_secondary
                                )
                            )
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.color_secondary
                                )
                            )
                            text = context.getText(R.string.remove_from_cart)
                        } else {
                            setOnClickListener { clickHandler.addToCart(adapterPosition) }
                            setTextColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.color_on_secondary
                                )
                            )
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.color_secondary
                                )
                            )
                            text = context.getText(R.string.add_to_cart)
                        }
                    } else {
                        isEnabled = false
                        setTextColor(ContextCompat.getColor(context, R.color.color_on_error))
                        setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.color_error
                            )
                        )
                        text = context.getText(R.string.out_of_stock)
                    }

                }

                textViewPrice.text = String.format(
                    "₹%.2f/%s",
                    product.price.toFloat() / product.quantity,
                    product.unit.toUpperCase()
                )
                textViewTitle.text = product.title

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CompactViewHolder(
        ItemCompactProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem.isInCart == newItem.isInCart &&
                    oldItem.price == newItem.price &&
                    oldItem.inStock == newItem.inStock
    }
}