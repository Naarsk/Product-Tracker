package com.example.product_tracker.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.product_tracker.adapter.ProductAdapter
import com.example.product_tracker.databinding.FragmentGalleryBinding
import com.example.product_tracker.example.ProductDataSource
import com.example.product_tracker.model.Product

class GalleryFragment : Fragment() {
    private var productType: String? = null
    private var binding: FragmentGalleryBinding? = null // Declare the binding variable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            productType = requireArguments().getString("productType")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val productRecyclerView = binding!!.recyclerView
        productRecyclerView.layoutManager = GridLayoutManager(context, 3)
        val productAdapter = context?.let { ProductAdapter(productList, it) }
        productRecyclerView.adapter = productAdapter
        return root
    }

    private val productList: ArrayList<Product>
        get() = ProductDataSource.getProductList(productType)

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}