package com.example.product_tracker.ui.product_manager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.product_tracker.R
import com.example.product_tracker.databinding.FragmentProductManagerBinding

class ProductManagerFragment : Fragment() {
    private var binding: FragmentProductManagerBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductManagerBinding.inflate(inflater, container, false)

        val root: View = binding!!.root
        val buttonCreateNew = root.findViewById<Button>(R.id.button_create_new)
        val buttonDeleteExisting = root.findViewById<Button>(R.id.button_edit_delete_existing)

        buttonCreateNew.setOnClickListener {
            Log.d("ProductManagerFragment", "buttonCreateNew clicked")
            val intent = Intent(requireContext(), CreateNewProductActivity::class.java)
            startActivity(intent)
        }
        buttonDeleteExisting.setOnClickListener {
            Log.d("ProductManagerFragment", "buttonDeleteExisting clicked")
            val intent = Intent(requireContext(), ShowExistingProductActivity::class.java)  //EditDeleteExistingProductActivity
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}