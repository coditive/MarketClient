package com.company.market.ui.orders

import android.content.res.Resources
import android.graphics.Typeface
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.market.data.UserOrder
import com.company.market.databinding.OrderItemBinding
import com.company.market.utils.Truss
import java.text.SimpleDateFormat
import kotlin.math.ceil


class OrdersAdapter : ListAdapter<UserOrder, OrdersAdapter.OrderViewHolder>(CALLBACK) {
    private fun Float.toSp(resources: Resources): Int =
        ceil(this * resources.displayMetrics.scaledDensity).toInt()

    inner class OrderViewHolder(private val binding: OrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userOrder: UserOrder) {
            binding.apply {
                orderTimestamp.text =
                    SimpleDateFormat("dd-MMM-yyyy HH:mm a").format(userOrder.timestamp).toString()
                orderStatus.text = userOrder.payment_status

                val truss = Truss()
                for (order in userOrder.order) {
                    truss
                        .pushSpan(AbsoluteSizeSpan(18f.toSp(orderStatus.resources)))
                        .append(order.product_name)
                        .append(" ")
                        .append(order.quantity.toString())
                        .append(" ")
                        .append(order.unit)
                        .append("\n")
                        .popSpan()
                }
                truss
                    .pushSpan(StyleSpan(Typeface.BOLD))
                    .pushSpan(AbsoluteSizeSpan(24f.toSp(orderStatus.resources)))
                    .append("\nTotal : ").append(
                        String.format(
                            "₹ %d",
                            userOrder.order.sumByDouble { it.total_item_cost }.toInt()
                        )
                    )

                orderDetails.text = truss.build()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrderViewHolder(
        OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun submitList(list: List<UserOrder>?) {
        super.submitList(
            if (list.isNullOrEmpty()) {
                listOf()
            } else {
                list.toList()
            }
        )
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) =
        holder.bind(getItem(position))

    private object CALLBACK : DiffUtil.ItemCallback<UserOrder>() {
        override fun areItemsTheSame(oldItem: UserOrder, newItem: UserOrder) =
            oldItem.order_id == newItem.order_id

        override fun areContentsTheSame(oldItem: UserOrder, newItem: UserOrder) = true
    }
}