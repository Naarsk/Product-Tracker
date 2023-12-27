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
import com.example.product_tracker.databinding.FragmentProductManagerBinding
import com.example.product_tracker.ui.product_manager.CreateNewProductActivity

class ProductManagerFragment : Fragment() {
    private var binding: FragmentProductManagerBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductManagerBinding.inflate(inflater, container, false)

        val root: View = binding!!.root
        val buttonCreateNew = root.findViewById<ImageButton>(R.id.button_create_new)
        val buttonEditExisting = root.findViewById<ImageButton>(R.id.button_edit_existing)
        val buttonDeleteExisting = root.findViewById<ImageButton>(R.id.button_delete_existing)

        buttonCreateNew.setOnClickListener {
            Log.d("ProductManagerFragment", "buttonCreateNew clicked")
            val intent = Intent(requireContext(), CreateNewProductActivity::class.java)
            startActivity(intent)
        }
        buttonEditExisting.setOnClickListener {
            Log.d("ProductManagerFragment", "buttonEditExisting clicked")
            val intent = Intent(requireContext(), CreateNewProductActivity::class.java)  //EditExistingProductActivity
            startActivity(intent)
        }
        buttonDeleteExisting.setOnClickListener {
            Log.d("ProductManagerFragment", "buttonDeleteExisting clicked")
            val intent = Intent(requireContext(), CreateNewProductActivity::class.java)  //DeleteExistingProductActivity
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}