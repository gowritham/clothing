package com.clothing.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clothing.R
import com.google.android.material.bottomnavigation.BottomNavigationView



class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_home, container, false)

        // Inflate the layout for this fragment
        val bottomNavigationView=view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        replaceFragment(ProductListingFragment())
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(ProductListingFragment())
                R.id.cart -> replaceFragment(MyCartFragment())
                R.id.favorite -> replaceFragment(product_details())
                R.id.profile -> replaceFragment(myprofileFragment())

            }
            true
        }
        return view
    }
    private fun replaceFragment(fragment : Fragment) {

        val fragmentManager= fragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frame_layout,fragment)
        fragmentTransaction?.commit()
    }
}