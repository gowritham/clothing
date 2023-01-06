package com.clothing.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clothing.R
import com.clothing.UI.retrofit.GridItemAdaptor
import com.clothing.UI.retrofit.ProductsApi
import com.clothing.UI.retrofit.ProductsData
import com.clothing.UI.retrofit.RetrofitHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductListingFragment : Fragment() {
    var urlList :ArrayList<String>? = arrayListOf()
    var titleList : ArrayList<String>? = arrayListOf()
    var priceList : ArrayList<String>? = arrayListOf()
    var gridItems : RecyclerView? = null
    lateinit var progressBar: ProgressBar

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_listing, container, false)
        gridItems = view.findViewById(R.id.gridItems)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE
        val productsApi = RetrofitHelper.getInstance().create(ProductsApi::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            val result = productsApi.getProducts()
            progressBar.visibility = View.GONE
            Log.d("data", result.body().toString())
            getData(result)
        }
        return view
    }

    private fun getData(result: Response<ProductsData>) {
        for(item in result.body()?.indices!!){
            result.body()?.get(item)?.let { urlList?.add(it.image)  }
            result.body()?.get(item)?.let { titleList?.add(it.title)  }
            result.body()?.get(item)?.let { priceList?.add(it.price.toString())  }

        }
        val adaptor = urlList?.let { titleList?.let { it1 -> priceList?.let { it2 -> GridItemAdaptor(it, it1, it2) } } }
        val gridLayout = GridLayoutManager(activity,2)
        gridItems?.layoutManager = gridLayout
        gridItems?.adapter = adaptor

        adaptor?.setOnItemClickListener(object : GridItemAdaptor.onItemClickListerner{
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString("image",result.body()?.get(position)?.image)
                bundle.putString("title",result.body()?.get(position)?.title)
                bundle.putString("price",result.body()?.get(position)?.price.toString())
                bundle.putString("description",result.body()?.get(position)?.description)
                view?.let { Navigation.findNavController(it).navigate(R.id.product_details,bundle) }
            }
        })


    }


}