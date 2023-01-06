package com.clothing.UI


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.clothing.R
import com.squareup.picasso.Picasso


class ProductDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_product_details2, container, false)
        val pdImageView3 = view.findViewById<ImageView>(R.id.pd_imageView3)
        val productDetailsTitle = view.findViewById<TextView>(R.id.product_details_title)
        val productDetailsPrice = view.findViewById<TextView>(R.id.product_details_price)
        val productDetailsDescription = view.findViewById<TextView>(R.id.product_details_description)
        Picasso.get().load(requireArguments().getString("image")).into(pdImageView3)
        productDetailsTitle.setText(requireArguments().getString("title"))
        productDetailsPrice.setText(requireArguments().getString("price"))
        productDetailsDescription.setText(requireArguments().getString("description"))
        return view

    }
}