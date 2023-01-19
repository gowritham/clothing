package com.clothing.UI.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.clothing.R

class CategoryAdaptor(val category: ArrayList<String>) :
    RecyclerView.Adapter<CategoryAdaptor.ViewHolder>() {


    class ViewHolder(itemView: View, ) : RecyclerView.ViewHolder(itemView) {
        val categoryList: TextView = itemView.findViewById(R.id.category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.design_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryList.text= category[position]
    }

    override fun getItemCount(): Int {
        //return category.size
        return category.size
    }
}