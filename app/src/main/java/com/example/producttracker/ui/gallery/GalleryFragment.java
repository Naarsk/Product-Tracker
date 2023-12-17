package com.example.producttracker.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producttracker.R;
import com.example.producttracker.adapter.ProductAdapter;
import com.example.producttracker.model.Product;
import com.example.producttracker.example.ProductDataSource;

import java.util.List;

public class GalleryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        RecyclerView productRecyclerView = root.findViewById(R.id.productRecyclerView);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        List<Product> productList = ProductDataSource.getProductList();
        ProductAdapter productAdapter = new ProductAdapter(productList, getContext());
        productRecyclerView.setAdapter(productAdapter);

        return root;
    }
}