package com.clothing.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clothing.R
import com.clothing.UI.retrofit.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import java.util.*
import kotlin.collections.ArrayList

class ProductListingFragment : Fragment() {
    var shared : String = "sharedPreference"
    var cartList : List<ProductsDataItem>? = null
    var searchList : List<ProductsDataItem>? = null
    var gridItems : RecyclerView? = null
    var categoryGridItems : RecyclerView? = null
    lateinit var progressBar: ProgressBar
    lateinit var searchview : SearchView
    lateinit var loading : TextView

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
        categoryGridItems = view.findViewById(R.id.recycler_view)
        searchview = view.findViewById(R.id.search)
        loading = view.findViewById(R.id.loading)

        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        val productsApi = RetrofitHelper.getInstance()?.create(ProductsApi::class.java)
        val categoryApi = RetrofitHelper.getInstance()?.create(ProductsApi::class.java)
        GlobalScope.launch(Dispatchers.Main) {
            val result = productsApi?.getProducts1()
            val categoryResult = categoryApi?.getCategoriesData()
            progressBar.visibility = View.GONE
            loading.visibility = View.GONE
            Log.d("data", result?.body().toString())
            if (result != null) {
                searchList = result.body()
            }
            if (result != null) {
                getData(result)
            }
            getCategoryData(categoryResult,result)
        }
        return view
    }

    private fun filterList(query: String?) {
        val filterList = ArrayList<ProductsDataItem>()
        if(query != null){
            for(i in searchList!!){
                if(i.title.lowercase(Locale.ROOT).contains(query)){
                    filterList.add(i)
                }
            }
        }
        if(filterList.isEmpty()){
            Toast.makeText(activity,"No Data Found",Toast.LENGTH_SHORT).show()
        }
        else{
            val adaptor = filterList.let { GridItemAdaptor(it) }
            val gridLayout = GridLayoutManager(activity,2)
            gridItems?.layoutManager = gridLayout
            gridItems?.adapter = adaptor
            adaptor.setOnItemClickListener(object : GridItemAdaptor.onItemClickListerner{
                override fun onItemClick(position: Int) {
                    val bundle = Bundle()
                    bundle.putString("id", filterList.get(position).id.toString())
                    view?.let { Navigation.findNavController(it).navigate(R.id.product_details,bundle) }
                }
            })
        }


    }

    private fun getCategoryData(
        categoryResult: Response<ResposeCategoryData>?,
        result: Response<List<ProductsDataItem>>?
    ) {
        val adaptor = categoryResult?.body()?.let { CategoryAdaptor(it) }
        val gridLayout = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
        categoryGridItems?.layoutManager = gridLayout
        categoryGridItems?.adapter = adaptor

        adaptor?.setOnItemClickListener(object : CategoryAdaptor.onItemClickListerner{
            override fun onItemClick(position: Int) {
                val categoryList = ArrayList<ProductsDataItem>()
                val categoryName = categoryResult.body()!!.get(position)
                Log.d("MMMMM",categoryName)
                for(i in result?.body()!!){
                    if(i.category == categoryName){
                        categoryList.add(i)
                    }
                }
                val adaptor1 = categoryList.let { GridItemAdaptor(it) }
                val gridLayout1 = GridLayoutManager(activity,2)
                gridItems?.layoutManager = gridLayout1
                gridItems?.adapter = adaptor1
                adaptor1.setOnItemClickListener(object : GridItemAdaptor.onItemClickListerner{
                    override fun onItemClick(position: Int) {
                        val bundle = Bundle()
                        bundle.putString("id", categoryList.get(position).id.toString())
                        view?.let { Navigation.findNavController(it).navigate(R.id.product_details,bundle) }
                    }
                })

            }

        })
    }


    private fun getData(result: Response<List<ProductsDataItem>>?) {
        cartList = result?.body()
        val adaptor = cartList?.let { GridItemAdaptor(it) }
        val gridLayout = GridLayoutManager(activity,2)
        gridItems?.layoutManager = gridLayout
        gridItems?.adapter = adaptor

        adaptor?.setOnItemClickListener(object : GridItemAdaptor.onItemClickListerner{
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString("id",result?.body()?.get(position)?.id.toString())
                view?.let { Navigation.findNavController(it).navigate(R.id.product_details,bundle) }
            }
        })


    }


}