package com.dipesh.onlinegadgetsstore.adapter

import android.app.AlertDialog
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dipesh.onlinegadgetsstore.R
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.entity.FavouriteItem
import com.dipesh.onlinegadgetsstore.repository.CartRepository
import com.dipesh.onlinegadgetsstore.repository.WishListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class FavouriteAdapter(
        val listProduct: ArrayList<FavouriteItem>,
        val context: Context
) : RecyclerView.Adapter<FavouriteAdapter.FavViewHolder>()  {
    class FavViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imgFavProductImage: ImageView
        val tvFavProductName: TextView
        val tvFavProductPrice: TextView
        val btnDeleteFavItem:ImageView

        init {
            imgFavProductImage = view.findViewById(R.id.imgFavProductImage)
            tvFavProductName = view.findViewById(R.id.tvFavProductName)
            tvFavProductPrice = view.findViewById(R.id.tvFavProductPrice)
            btnDeleteFavItem = view.findViewById(R.id.imgBtnFavItem)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fav_item_display, parent, false)
        return FavViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val product = listProduct[position]
        val wishItems = product.wishItems
        if(wishItems != null){
            for (i in 0..wishItems.size - 1){
                holder.tvFavProductName.text=product.wishItems?.get(i).name
                holder.tvFavProductPrice.text=product.wishItems?.get(i).price.toString()
            }
        }

        val imagePath = ServiceBuilder.loadImagePath() + product.wishItems?.get(0)!!.productPictures
        if (!product.wishItems?.get(0)!!.productPictures.equals("noimage.png")) {
            Glide.with(context).load(imagePath).into(holder.imgFavProductImage)
        }

        holder.btnDeleteFavItem.setOnClickListener {
            val builder= AlertDialog.Builder(context)
            builder.setTitle("Delete Cart Item")
            builder.setMessage("Are you sure you want to delete this item?")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Delete") {_,_ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try{
                        val wishRepository = WishListRepository()
                        val response = wishRepository.deleteFavItem(product._id!!)
                        if(response.success==true){
                            withContext(Dispatchers.Main){
                                Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT).show()
                            }
                            withContext(Dispatchers.Main){
                                listProduct.remove(product)
                                notifyDataSetChanged()

                            }
                        }
                    }catch (ex : Exception){
                        withContext(Dispatchers.Main){
                            Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            builder.setNegativeButton("Cancel"){_,_ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }

}