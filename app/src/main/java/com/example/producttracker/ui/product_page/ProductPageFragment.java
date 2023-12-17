package com.example.producttracker.ui.product_page;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.producttracker.R;
import com.example.producttracker.model.Product;

public class ProductPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_page, container, false);

        // Find and initialize views
        ImageView imageView = view.findViewById(R.id.anotherImageView);
        TextView priceTextView = view.findViewById(R.id.priceTextView);
        TextView typeTextView = view.findViewById(R.id.typeTextView);
        TextView colorTextView = view.findViewById(R.id.colorTextView);
        TextView quantityTextView = view.findViewById(R.id.quantityTextView);

        // Get the product details from the arguments bundle
        Bundle arguments = getArguments();
        if (arguments != null) {
            Product product = (Product) arguments.getSerializable("product");
            if (product != null) {
                // Set the values from the Product object
                imageView.setImageURI(Uri.parse(product.getImageUrl()));
                priceTextView.setText(String.valueOf(product.getPrice()));
                typeTextView.setText(product.getType());
                quantityTextView.setText(String.valueOf(product.getQuantity()));
                colorTextView.setText(product.getColor());
            }
        }

        // TODO: Add any additional initialization or logic

        return view;
    }
}