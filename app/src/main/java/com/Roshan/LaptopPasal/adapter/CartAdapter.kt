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
import com.dipesh.onlinegadgetsstore.entity.Cart
import com.dipesh.onlinegadgetsstore.repository.CartRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CartAdapter(
        val lstProduct: ArrayList<Cart>,
        val context: Context

) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCartProductImage: ImageView
        val tvCartProductName: TextView
        val tvCartProductPrice: TextView
        val btnDeleteCartItem: ImageView

        init {
            imgCartProductImage = view.findViewById(R.id.imgCartProductImage)
            tvCartProductName = view.findViewById(R.id.tvCartProductName)
            tvCartProductPrice = view.findViewById(R.id.tvCartProductPrice)
            btnDeleteCartItem = view.findViewById(R.id.imgBtnDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.cart_display, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = lstProduct[position]
        val cartItems = product.cartItems
        if (cartItems != null) {
            for (i in 0..cartItems.size - 1) {
                holder.tvCartProductName.text = product.cartItems?.get(i).name
                holder.tvCartProductPrice.text = product.cartItems?.get(i).price.toString()
            }
        }
        val imagePath = ServiceBuilder.loadImagePath() + product.cartItems?.get(0)!!.productPictures
        if (!product.cartItems?.get(0)!!.productPictures.equals("noimage.png")) {
            Glide.with(context).load(imagePath).into(holder.imgCartProductImage)
        }
        holder.btnDeleteCartItem.setOnClickListener {
            val builder= AlertDialog.Builder(context)
            builder.setTitle("Delete Cart Item")
            builder.setMessage("Are you sure you want to delete this item?")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Delete") {_,_ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try{
                        val cartRepository = CartRepository()
                        val response = cartRepository.deleteCart(product._id!!)
                        if(response.success==true){
                            withContext(Dispatchers.Main){
                                Toast.makeText(context, "Item deleted successfully", Toast.LENGTH_SHORT).show()
                            }
                            withContext(Main){
                                lstProduct.remove(product)
                                notifyDataSetChanged()

                            }
                        }
                    }catch (ex :Exception){
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
        return lstProduct.size
    }
}