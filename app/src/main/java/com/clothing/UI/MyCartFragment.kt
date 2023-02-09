package com.clothing.UI

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clothing.R
import com.clothing.UI.retrofit.ProductsDataItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.properties.Delegates

class MyCartFragment : Fragment() {
    var shared : String = "sharedPreference"
    lateinit var totalPrice : TextView
    lateinit var subTotal : TextView
    lateinit var checkoutBtn : AppCompatButton

    var cartList : List<ProductsDataItem>? = null
    var titleList : ArrayList<String>? = arrayListOf()
    var priceList : ArrayList<String>? = arrayListOf()
    var urlList1 :ArrayList<String>? = arrayListOf()
    var cart_grid_items : RecyclerView? = null

    lateinit var cart : TextView


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
        val counter:Int=prefs.getInt("count",0)
        val id = prefs.getInt("id",0)
        Log.d("CCCC","$id")
        var start=0
        while(start<counter){
            titleList?.add(prefs.getString("Title"+start,"Unknown").toString())
            priceList?.add(prefs.getString("Price"+start,"Unknown").toString())
            urlList1?.add(prefs.getString("Image"+start,"Unknown").toString())
            start += 1
        }

        var total = 0
        for(i in priceList?.indices!!){
            total += priceList!!.get(i).toDouble().toInt()
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

        val newList = titleList?.toSet()?.toMutableList()
        val newPrice = priceList?.toSet()?.toMutableList()
        val newUrl = urlList1?.toSet()?.toMutableList()
        val adaptor = newList?.let { newPrice?.let { it1 -> newUrl?.let { it2 -> CartAdaptor(it as ArrayList<String>, it1 as ArrayList<String>, it2 as ArrayList<String>) } } }
        val gridLayout = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        cart_grid_items?.layoutManager=gridLayout
        cart_grid_items?.adapter = adaptor

        Log.d("MMMM",titleList?.toString()!!)
        adaptor?.setOnItemClickListener(object : CartAdaptor.onItemClickListerner{
            override fun onItemClick(position: Int) {
                total += position
                totalPrice.text = total.toString()
            }

            override fun onClick(position: Int) {
                total -= position
                totalPrice.text = total.toString()
            }

            override fun onDelete(position: Int, price: Int) {
                total -= price
                totalPrice.text = total.toString()
                Toast.makeText(activity,"$position",Toast.LENGTH_SHORT).show()

            }


        })
        checkoutBtn.setOnClickListener {
            val editor = prefs.edit()
            editor.putInt("subTotal",total)
            editor.apply()
            Toast.makeText(activity,"go to check out page",Toast.LENGTH_SHORT).show()
        }
        return view
    }


}