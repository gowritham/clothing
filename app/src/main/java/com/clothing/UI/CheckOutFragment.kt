package com.clothing.UI

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.clothing.R

class CheckOutFragment : Fragment() {
    var shared : String = "sharedPreference"
    lateinit var subTotalPrice : TextView
    lateinit var totalBill : TextView
    lateinit var delivary : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_check_out, container, false)
        subTotalPrice = view.findViewById(R.id.tv_subtotal_price)
        totalBill = view.findViewById(R.id.tv_total_bill)
        delivary = view.findViewById(R.id.tv_delivery_fee_price)
        val appContext = requireContext().applicationContext
        val prefs = appContext.getSharedPreferences(shared, Context.MODE_PRIVATE)
        val total = delivary.text.toString().toInt() + prefs.getInt("subTotal",0)
        subTotalPrice.text = prefs.getInt("subTotal",0).toString()
        totalBill.text = total.toString()

        return view
    }

}