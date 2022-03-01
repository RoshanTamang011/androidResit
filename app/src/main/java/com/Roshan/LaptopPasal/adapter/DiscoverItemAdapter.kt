package com.dipesh.onlinegadgetsstore.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dipesh.onlinegadgetsstore.R
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.entity.DiscoverItem
import com.dipesh.onlinegadgetsstore.model.DiscoverItemModel
import com.dipesh.onlinegadgetsstore.ui.ProductDetailActivity

class DiscoverItemAdapter(
        val listProducts: ArrayList<DiscoverItem>,
        val context:Context

) :RecyclerView.Adapter<DiscoverItemAdapter.ProductViewHolder>(){
    class ProductViewHolder(view: View):RecyclerView.ViewHolder(view){
        val productImage: ImageView
        val tvProductName:TextView
        val tvProductPrice:TextView

        init {
            productImage=view.findViewById(R.id.productImage)
            tvProductName=view.findViewById(R.id.tvProductName)
            tvProductPrice=view.findViewById(R.id.tvProductPrice)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_display,parent,false)
        return ProductViewHolder(view)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product= listProducts[position]

        holder.tvProductName.text=product.name
        holder.tvProductPrice.text=product.price.toString()
        val imagePath=ServiceBuilder.loadImagePath() + product.productPictures
        if(!product.productPictures.equals("noimage.png")){
            Glide.with(context)
                    .load(imagePath)
                    .fitCenter()
                    .into(holder.productImage)
        }

        holder.productImage.setOnClickListener{
            val intent= Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("product",product)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }
}