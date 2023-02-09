package com.clothing.UI.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.clothing.R

class CategoryAdaptor(val category: ArrayList<String>) :
    RecyclerView.Adapter<CategoryAdaptor.ViewHolder>() {
    private lateinit var mListener : onItemClickListerner
    interface onItemClickListerner{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener : onItemClickListerner){
        mListener = listener
    }

    class ViewHolder(itemView: View,listener: onItemClickListerner ) : RecyclerView.ViewHolder(itemView) {
        val categoryList: TextView = itemView.findViewById(R.id.category)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.design_layout,parent,false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.categoryList.text= category[position]
    }

    override fun getItemCount(): Int {
        return category.size
    }
}