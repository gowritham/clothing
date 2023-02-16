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
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.clothing.R
import com.clothing.UI.retrofit.ProductsApi
import com.clothing.UI.retrofit.RetrofitHelper
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ProductDetails : Fragment() {
    lateinit var id : String
    var shared : String = "sharedPreference"
    lateinit var title : String
    lateinit var price : String
    lateinit var image : String
    var count : Int = 0


    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
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
        val addToCart = view.findViewById<AppCompatButton>(R.id.pd_button)
        val pbar = view.findViewById<ProgressBar>(R.id.pid)
        val t = view.findViewById<TextView>(R.id.t)
        val card = view.findViewById<CardView>(R.id.cardViewss)
        val bar = view.findViewById<MaterialToolbar>(R.id.bar)
        val appContext = requireContext().applicationContext
        val prefs = appContext.getSharedPreferences(shared, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        id = requireArguments().getString("id").toString()
        pbar.visibility = View.VISIBLE
        t.visibility = View.VISIBLE
        addToCart.visibility = View.GONE
        card.visibility = View.GONE
        bar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_product_details_to_homeFragment)
        }


        val productsAPI = RetrofitHelper.getInstance()?.create(ProductsApi::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            val result = productsAPI?.eachProduct(id)
            pbar.visibility = View.GONE
            t.visibility = View.GONE
            addToCart.visibility = View.VISIBLE
            card.visibility = View.VISIBLE
            Log.d("datasssssss","${result?.body()}")
            Picasso.get().load(result?.body()?.image).into(pdImageView3)
            productDetailsTitle.text = result?.body()?.title
            productDetailsPrice.text = result?.body()?.price.toString()
            productDetailsDescription.text = result?.body()?.description
            title = result?.body()?.title.toString()
            price = result?.body()?.price.toString()
            image = result?.body()?.image.toString()


        }
        addToCart.setOnClickListener {
            val sharedTitles = prefs.all.values
            if(sharedTitles.contains(title) == false) {

                Toast.makeText(activity, "Item Added to Cart", Toast.LENGTH_SHORT).show()
                val counter: Int = prefs.getInt("count", 0)
                editor.putInt("id", id.toInt())
                editor.putString("Title" + counter, title)
                editor.putString("Price" + counter, price)
                editor.putString("Image" + counter, image)
                editor.putInt("count", counter + 1)
                editor.apply()
            }
            else{
                addToCart.setText("Go to Cart")
            }


        }
        return view

    }

}