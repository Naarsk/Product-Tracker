package com.example.producttracker.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producttracker.R;
import com.example.producttracker.adapter.ProductAdapter;
import com.example.producttracker.model.Product;

import java.util.List;
public class GalleryFragment extends Fragment {
    private final List<Product> productList;

    public GalleryFragment(List<Product> productList) {
        this.productList = productList;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static GalleryFragment newInstance(List<Product> productList) {
        return new GalleryFragment(productList);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        RecyclerView productRecyclerView = root.findViewById(R.id.recyclerView);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        ProductAdapter productAdapter = new ProductAdapter(productList, getContext());
        productRecyclerView.setAdapter(productAdapter);

        return root;
    }
}