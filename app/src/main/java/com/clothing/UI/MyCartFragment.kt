package com.clothing.UI

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.clothing.R

class MyCartFragment : Fragment() {
    lateinit var tvItemCount : TextView
    lateinit var add: ImageView
    lateinit var subtract : ImageView
    lateinit var tvItemCount1 : TextView
    lateinit var add1 : ImageView
    lateinit var subtract1 : ImageView
    lateinit var totalPrice : TextView
    lateinit var price1 : TextView
    lateinit var price2 : TextView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_cart, container, false)
        tvItemCount = view.findViewById(R.id.tv_item_count)
        add = view.findViewById(R.id.add)
        subtract = view.findViewById(R.id.subtract)
        tvItemCount1 = view.findViewById(R.id.tv_item_count1)
        add1 = view.findViewById(R.id.add1)
        subtract1 = view.findViewById(R.id.subtract1)
        totalPrice = view.findViewById(R.id.tv_total_price)
        price1 = view.findViewById(R.id.tv_price)
        price2 = view.findViewById(R.id.tv_price1)
        var product1 = tvItemCount.text.toString().toInt()
        var product2 = tvItemCount1.text.toString().toInt()
        var total = totalPrice.text.toString().toInt()
        add.setOnClickListener {
            tvItemCount.text = (product1 + 1).toString()
            product1 += 1
            totalPrice.text = (total + price1.text.toString().toInt()).toString()
            total += price1.text.toString().toInt()
        }
        subtract.setOnClickListener {
            if(product1 != 0 ) {
                tvItemCount.text = (product1 - 1).toString()
                product1 -= 1
                totalPrice.text = (total - price1.text.toString().toInt()).toString()
                total -= price1.text.toString().toInt()
            }
        }
        add1.setOnClickListener {
            tvItemCount1.text = (product2 + 1).toString()
            product2 += 1
            totalPrice.text = (total + price2.text.toString().toInt()).toString()
            total += price2.text.toString().toInt()
        }
        subtract1.setOnClickListener {
            if(product2 != 0){
                tvItemCount1.text = (product2 - 1).toString()
                product2 -= 1
                totalPrice.text = (total - price2.text.toString().toInt()).toString()
                total -= price2.text.toString().toInt()
            }
        }
        return view
    }


}