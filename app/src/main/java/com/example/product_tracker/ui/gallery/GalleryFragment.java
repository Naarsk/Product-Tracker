package com.example.product_tracker.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.producttracker.R;
import com.example.producttracker.databinding.FragmentGalleryBinding;
import com.example.producttracker.ui.gallery.GalleryViewModel;

import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}
public static class GalleryFragment extends Fragment {
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

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView productRecyclerView = root.findViewById(R.id.recyclerView);
        productRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        ProductAdapter productAdapter = new ProductAdapter(productList, getContext());
        productRecyclerView.setAdapter(productAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
