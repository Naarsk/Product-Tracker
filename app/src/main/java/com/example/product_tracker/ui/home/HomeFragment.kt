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
import com.example.product_tracker.model.ProductTypeMapper

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        val typeValues = ProductTypeMapper(requireContext()).getAllTypeValues()

        val buttons = listOf(
            Pair(R.id.button_bags, typeValues[0]),
            Pair(R.id.button_wallets, typeValues[1]),
            Pair(R.id.button_gloves, typeValues[2]),
            Pair(R.id.button_suitcases,typeValues[3]),
            Pair(R.id.button_belts, typeValues[4]),
            Pair(R.id.button_accessories, typeValues[5])
        )

        buttons.forEach { (buttonId, productType) ->
            val button = root.findViewById<ImageButton>(buttonId)
            button.setOnClickListener {
                Log.d("HomeFragment", "$productType button clicked")
                val intent = Intent(requireContext(), ProductListActivity::class.java)
                intent.putExtra("productType", productType)
                startActivity(intent)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}