package com.example.producttracker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.producttracker.R;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ImageButton button1 = root.findViewById(R.id.button1);
        ImageButton button2 = root.findViewById(R.id.button2);
        ImageButton button3 = root.findViewById(R.id.button3);

        TextView text1 = root.findViewById(R.id.text1);
        TextView text2 = root.findViewById(R.id.text2);
        TextView text3 = root.findViewById(R.id.text3);

        button1.setOnClickListener(view -> {
            // Handle button1 click event
            // Display products with type "bags" using a gallery
            Bundle bundle = new Bundle();
            bundle.putString("productType", "bags");
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_gallery, bundle);
        });

        button2.setOnClickListener(view -> {
            // Handle button2 click event
            // Display products with type "gloves" using a gallery
            Bundle bundle = new Bundle();
            bundle.putString("productType", "gloves");
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_gallery, bundle);
        });

        button3.setOnClickListener(view -> {
            // Handle button3 click event
            // Display products with type "wallets" using a gallery
            Bundle bundle = new Bundle();
            bundle.putString("productType", "wallets");
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_nav_gallery, bundle);
        });

        return root;
    }
}