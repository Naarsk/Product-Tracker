package com.example.producttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.producttracker.example.ProductDataSource;
import com.example.producttracker.model.Product;
import com.example.producttracker.ui.gallery.GalleryFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageButton button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGalleryFragment();
            }
        });
    }

    private void navigateToGalleryFragment() {
        List<Product> productList = ProductDataSource.getProductList();
        GalleryFragment galleryFragment = GalleryFragment.newInstance(productList);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentHome, galleryFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}