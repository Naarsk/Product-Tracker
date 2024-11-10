package com.example.product_tracker.ui.product_manager

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.product_tracker.MainApplication
import com.example.product_tracker.R
import com.example.product_tracker.model.Product
import com.example.product_tracker.util.Utils

class EditDeleteAdapter(private val context: Context) :
    RecyclerView.Adapter<EditDeleteAdapter.EditDeleteHolder>() {

    private val productDao = MainApplication.productDao
    private var productList: List<Product> = productDao.getAllProduct()

    class EditDeleteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productImageView: ImageView = itemView.findViewById(R.id.product_image)
        var productNameView: TextView = itemView.findViewById(R.id.product_name)

    }    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditDeleteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_product, parent, false)
        return EditDeleteHolder(view)
    }

    override fun onBindViewHolder(holder: EditDeleteHolder, position: Int) {
        val product = productList[position]
        val productName : String = product.color + " " + product.type

        holder.productNameView.text = productName

        Glide.with(context).load(product.imagePath).apply(RequestOptions().fitCenter()).into(holder.productImageView)
        holder.productImageView.setOnClickListener {
            Log.d("EditDeleteAdapter", "Product image clicked: $product")
            showEditDeletePopup(product)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showEditDeletePopup(product: Product) {
        val options = arrayOf(
            context.getString(R.string.edit_option),
            context.getString(R.string.delete_option)
        )
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.choose_option))
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> {
                    // Edit option selected
                    // Implement your edit logic here
                }

                1 -> {
                    // Delete option selected
                    // Delete product image
                    Utils().deleteFile(product.imagePath)
                    //Delete product info from database
                    val deleted = productDao.deleteProduct(productId=product.id)
                    if (deleted == 1) {
                        productList = productDao.getAllProduct()
                        notifyDataSetChanged()
                        Log.d("DeleteOption", "Product deleted: $product")
                    } else {
                        Log.e("DeleteOption", "Failed to delete product: $product")
                    }
                }
            }
        }
        builder.show()
    }
}