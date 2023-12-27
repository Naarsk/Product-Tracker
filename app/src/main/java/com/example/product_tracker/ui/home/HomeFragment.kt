package com.example.product_tracker.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.example.product_tracker.R
import com.example.product_tracker.databinding.FragmentHomeBinding
import com.example.product_tracker.GalleryActivity

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        val button1 = root.findViewById<ImageButton>(R.id.button1)
        val button2 = root.findViewById<ImageButton>(R.id.button2)
        val button3 = root.findViewById<ImageButton>(R.id.button3)
        button1.setOnClickListener {
            // Handle button1 click event
            // Display products with type "bags" using a gallery
            Log.d("HomeFragment", "button1 clicked")
            val intent = Intent(requireContext(), GalleryActivity::class.java)
            intent.putExtra("productType", "bag")
            startActivity(intent)
        }

        button2.setOnClickListener {
            // Handle button2 click event
            // Display products with type "gloves" using a gallery
            val intent = Intent(requireContext(), GalleryActivity::class.java)
            intent.putExtra("productType", "glove")
            startActivity(intent)

        }
        button3.setOnClickListener {
            // Handle button3 click event
            // Display products with type "wallets" using a gallery
            val intent = Intent(requireContext(), GalleryActivity::class.java)
            intent.putExtra("productType", "wallet")
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}