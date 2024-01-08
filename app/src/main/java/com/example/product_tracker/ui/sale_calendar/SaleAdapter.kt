package com.example.product_tracker.ui.sale_calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.product_tracker.R
import com.example.product_tracker.model.Sale

class SaleAdapter(var sales: List<Sale>) : RecyclerView.Adapter<SaleAdapter.SaleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sale, parent, false)
        return SaleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        val sale = sales[position]
        holder.bind(sale)
    }

    override fun getItemCount(): Int {
        return sales.size
    }

    fun refreshData(newSales: List<Sale>) {
        sales = newSales
        notifyDataSetChanged()
    }
    inner class SaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // private val titleTextView: TextView = itemView.findViewById(R.id.saleTitleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.saleDescriptionTextView)

        fun bind(sale: Sale) {
            // titleTextView.text = sale.product.type

            val typeStr = itemView.context.getString(R.string.type)
            val idStr = itemView.context.getString(R.string.id)
            val colorStr = itemView.context.getString(R.string.color)

            val saleDescription = typeStr + ": " + sale.product.type + ", " + idStr + ": " +
                    sale.product.id + ", " + colorStr + ": " + sale.product.color
            descriptionTextView.text = saleDescription
        }
    }
}