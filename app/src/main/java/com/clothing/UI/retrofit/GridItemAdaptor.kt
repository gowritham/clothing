package com.clothing.UI.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.clothing.R
import com.squareup.picasso.Picasso

class GridItemAdaptor(val url : ArrayList<String>, val title : ArrayList<String>, val price : ArrayList<String>) :
    RecyclerView.Adapter<GridItemAdaptor.ViewHolder>() {
    private lateinit var mListener : onItemClickListerner
    interface onItemClickListerner{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener : onItemClickListerner){
        mListener = listener
    }
    class ViewHolder(itemView: View,listener: onItemClickListerner) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.image)
        val tvTitle : TextView = itemView.findViewById(R.id.tv_title)
        val tvPrice : TextView = itemView.findViewById(R.id.tv_price)
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_view,parent,false)
        return ViewHolder(view,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = title[position]
        holder.tvPrice.text = price[position]
        Picasso.get().load(url[position]).into(holder.image)
    }

    override fun getItemCount(): Int {
        return title.size
    }
}