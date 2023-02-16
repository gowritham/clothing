package com.clothing.UI

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clothing.R
import com.clothing.UI.retrofit.Items

class MyCartFragment : Fragment() {
    var shared : String = "sharedPreference"
    lateinit var totalPrice : TextView
    lateinit var subTotal : TextView
    lateinit var checkoutBtn : AppCompatButton

    var cartList : ArrayList<Items>? = arrayListOf()
    var cart_grid_items : RecyclerView? = null

    lateinit var cart : TextView
    var badge= 0


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_cart, container, false)
        totalPrice = view.findViewById(R.id.tv_total_price)
        cart_grid_items = view.findViewById(R.id.recycle_cart1)
        cart = view.findViewById(R.id.emptyCart)
        subTotal = view.findViewById(R.id.tv_subtotal)
        checkoutBtn = view.findViewById(R.id.checkoutBtn)

        val appContext = requireContext().applicationContext
        val prefs = appContext.getSharedPreferences(shared, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val counter:Int=prefs.getInt("count",0)
        var start=0
        while(start<counter){
            if(prefs.getString("Title"+start,"Unknown").toString() != "Unknown") {
                cartList?.add(
                    Items(
                        prefs.getString("Title" + start, "Unknown").toString(),
                        prefs.getString("Price" + start, "Unknown").toString(),
                        prefs.getString("Image" + start, "Unknown").toString()
                    )
                )
            }
            start += 1
        }
        var total = 0
        for(i in cartList?.indices!!){
            total += cartList!!.get(i).price.toDouble().toInt()
        }
        totalPrice.text = total.toString()
        if(total == 0){
            cart.visibility = View.VISIBLE
            subTotal.visibility = View.GONE
            checkoutBtn.visibility = View.GONE
            totalPrice.visibility = View.GONE

        }
        else{
            cart.visibility = View.GONE
            subTotal.visibility = View.VISIBLE
            checkoutBtn.visibility = View.VISIBLE
            totalPrice.visibility = View.VISIBLE
        }

        val adaptor= cartList?.let { CartAdaptor(it) }
        val gridLayout = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        cart_grid_items?.layoutManager=gridLayout
        cart_grid_items?.adapter = adaptor

        adaptor?.setOnItemClickListener(object : CartAdaptor.onItemClickListerner{
            override fun onItemClick(position: Int) {
                total += position
                totalPrice.text = total.toString()
            }

            override fun onClick(position: Int) {
                total -= position
                totalPrice.text = total.toString()
            }

            override fun onDelete(position: Int, price: Int, title: String) {
                total -= price
                totalPrice.text = total.toString()
                if(total == 0){
                    cart.visibility = View.VISIBLE
                    subTotal.visibility = View.GONE
                    checkoutBtn.visibility = View.GONE
                    totalPrice.visibility = View.GONE

                }
                val sharedKey : MutableSet<String> = prefs.all.keys
                val sharedList = sharedKey.toMutableList()
                val sharedValues = prefs.all.values
                if(sharedValues.contains(title)){
                    val index = sharedValues.indexOf(title)
                    val itemTitle = sharedList.get(index)
                    editor.remove(itemTitle)
                    editor.apply()
                }

            }


        })
        checkoutBtn.setOnClickListener {
            editor.putInt("subTotal",total)
            editor.apply()
            Toast.makeText(activity,"go to check out page",Toast.LENGTH_SHORT).show()
        }
        return view
    }


}