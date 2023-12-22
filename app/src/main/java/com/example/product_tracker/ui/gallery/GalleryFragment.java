package com.example.product_tracker.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.product_tracker.adapter.ProductAdapter;
import com.example.product_tracker.example.ProductDataSource;
import com.example.product_tracker.model.Product;
import com.example.product_tracker.R;
import com.example.product_tracker.databinding.FragmentGalleryBinding;

import java.util.List;

public class GalleryFragment extends Fragment {
    private String productType;
    private FragmentGalleryBinding binding; // Declare the binding variable

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productType = getArguments().getString("productType");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView productRecyclerView = root.findViewById(R.id.recyclerView);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        ProductAdapter productAdapter = new ProductAdapter(getProductList(), getContext());
        productRecyclerView.setAdapter(productAdapter);

        return root;
    }

    private List<Product> getProductList() {
        List<Product> productList = ProductDataSource.getProductList(productType);
        return productList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}