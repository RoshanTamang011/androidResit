package com.dipesh.onlinegadgetsstore.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.dipesh.onlinegadgetsstore.R
import com.dipesh.onlinegadgetsstore.api.ServiceBuilder
import com.dipesh.onlinegadgetsstore.entity.DiscoverItem
import com.dipesh.onlinegadgetsstore.repository.CartRepository
import com.dipesh.onlinegadgetsstore.repository.WishListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var imgProductDetail: ImageView
    private lateinit var tvProductDetailName: TextView
    private lateinit var tvProductDetailPrice: TextView
    private lateinit var tvProductDetailDescription: TextView
    private lateinit var btnAddToCart: AppCompatButton
    private lateinit var btnAddToFav: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        imgProductDetail=findViewById(R.id.imgProductDetail)
        tvProductDetailName=findViewById(R.id.tvProductDetailName)
        tvProductDetailPrice=findViewById(R.id.tvProductDetailPrice)
        tvProductDetailDescription=findViewById(R.id.tvProductDetailDescription)
        btnAddToCart=findViewById(R.id.btnAddToCart)
        btnAddToFav=findViewById(R.id.btnAddFav)

        val intent =intent!!.getParcelableExtra<DiscoverItem>("product")
        val imagePath = ServiceBuilder.loadImagePath() + intent !!.productPictures
        if(!intent.productPictures.equals("noimage.png")){
            Glide.with(this)
                    .load(imagePath).fitCenter().into(imgProductDetail)
        }

        tvProductDetailName.text=intent!!.name
        tvProductDetailPrice.text=intent!!.price.toString()
        tvProductDetailDescription.text=intent!!.description
        btnAddToCart.setOnClickListener {
            val repository= CartRepository()
            CoroutineScope (Dispatchers.Main).launch{
                Log.d("Token is ",ServiceBuilder.token.toString())
                val response=repository.addToCart(productId = intent._id!!)
                if(response.success==true){
                    Log.d("Item added to the cart ", ".OK")
                    Toast.makeText(this@ProductDetailActivity, "Item added to the cart", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@ProductDetailActivity, "Failed to add product to cart", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnAddToFav.setOnClickListener {
            val repository= WishListRepository()
            CoroutineScope (Dispatchers.Main).launch{
                Log.d("Token is ",ServiceBuilder.token.toString())
                val response=repository.addtofav(productId = intent._id!!)
                if(response.success==true){
                    Log.d("Item Added To WishList ", ".OK")
                    Toast.makeText(this@ProductDetailActivity, "Item Added To WishList", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@ProductDetailActivity, "Failed to add product to Wishlist", Toast.LENGTH_SHORT).show()
                }
            }

        }


    }
}