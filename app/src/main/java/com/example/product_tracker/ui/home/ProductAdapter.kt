package com.example.product_tracker.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.product_tracker.R
import com.example.product_tracker.model.Product


class ProductAdapter(
    private val productList: ArrayList<Product>,
    private val context: Context) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImageView: ImageView
        var productNameView: TextView

        init {
            productImageView = itemView.findViewById(R.id.product_image)
            productNameView = itemView.findViewById(R.id.product_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        val productName: String = product.color + " " + product.type

        holder.productNameView.text = productName
        Glide.with(context)
            .load(product.imagePath)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .fitCenter()
            .into(holder.productImageView)


        holder.productImageView.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("name", productName)
            intent.putExtra("product", product)
            (context as AppCompatActivity).startActivityForResult(intent, REQUEST_CODE_PRODUCT)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
    companion object {
        const val REQUEST_CODE_PRODUCT = 1
    }
}