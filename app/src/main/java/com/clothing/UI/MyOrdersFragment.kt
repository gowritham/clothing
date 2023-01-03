package com.clothing.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.clothing.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MyOrdersFragment : Fragment() {

    var tabTitle=arrayOf("Completed","Cancelled")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_my_orders, container, false)
        val tl=view.findViewById<TabLayout>(R.id.tabLayout)
        val pager=view.findViewById<ViewPager2>(R.id.viewPager)
        pager.adapter= ViewPagerAdapter(parentFragmentManager,lifecycle)

        TabLayoutMediator(tl,pager){
                tab,position ->
            tab.text=tabTitle[position]
        }.attach()


        return view
    }
}