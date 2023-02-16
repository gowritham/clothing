package com.clothing.UI

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.clothing.R
import com.clothing.UI.retrofit.GridItemAdaptor
import com.clothing.UI.retrofit.Items
import com.clothing.UI.retrofit.ProductsData
import com.clothing.UI.retrofit.ProductsDataItem
import com.squareup.picasso.Picasso
class CartAdaptor(val titleL: ArrayList<Items>) :
    RecyclerView.Adapter<CartAdaptor.ViewHolder>() {
        private lateinit var mListener : onItemClickListerner
        interface onItemClickListerner{
            fun onItemClick(position: Int)
            fun onClick(position: Int)
            fun onDelete(position: Int,price : Int,title : String)
        }
        fun setOnItemClickListener(listener : onItemClickListerner){
            mListener = listener
        }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.cartItemImage)
        val tvTitle: TextView = itemView.findViewById(R.id.cartItemTitle)
        val tvPrice: TextView = itemView.findViewById(R.id.cartItemPrice)
        val quantity : TextView = itemView.findViewById(R.id.quantity)
        var remove = itemView.findViewById<ImageView>(R.id.remove)
        var adding = itemView.findViewById<ImageView>(R.id.adding)
        var delete = itemView.findViewById<ImageView>(R.id.delete)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_card_item,parent,false)
        return ViewHolder(view)
    }


    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvTitle.text = titleL.get(position).title
            holder.tvPrice.text = titleL.get(position).price
            Picasso.get().load(titleL.get(position).image).into(holder.image)

            var totalPrice = 0
            val eachTotal = titleL.get(position).price.toDouble().toInt()
            totalPrice = totalPrice + eachTotal
            var quantity : Int= holder.quantity.text.toString().toInt()
            holder.adding.setOnClickListener {
                holder.quantity.text = (quantity + 1).toString()
                quantity += 1
                //eachTotal += priceL.get(position).toDouble().toInt()
                totalPrice += eachTotal
                holder.tvPrice.text = totalPrice.toString()
                mListener.onItemClick(eachTotal)

            }
            holder.remove.setOnClickListener {
                Log.d("EEEE", position.toString())
                if(quantity != 1){
                    holder.quantity.text = (quantity - 1).toString()
                    quantity -= 1
                    //eachTotal -= priceL.get(position).toDouble().toInt()
                    totalPrice -= eachTotal
                    holder.tvPrice.text = totalPrice.toString()

                    mListener.onClick(eachTotal)
                }
            }
            holder.delete.setOnClickListener {
                mListener.onDelete(position,totalPrice,titleL.get(position).title)
                titleL.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
                notifyDataSetChanged()

            }


    }

    override fun getItemCount(): Int {
        return titleL.size
    }

}