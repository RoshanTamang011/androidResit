package com.dipesh.onlinegadgetsstore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dipesh.onlinegadgetsstore.R
import com.dipesh.onlinegadgetsstore.adapter.CartAdapter
import com.dipesh.onlinegadgetsstore.adapter.FavouriteAdapter
import com.dipesh.onlinegadgetsstore.database.RoomDb
import com.dipesh.onlinegadgetsstore.entity.Cart
import com.dipesh.onlinegadgetsstore.entity.FavouriteItem
import com.dipesh.onlinegadgetsstore.repository.CartRepository
import com.dipesh.onlinegadgetsstore.repository.WishListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishlistFragment : Fragment() {
    private lateinit var rvDisplayFavItems: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_wishlist, container, false)
        rvDisplayFavItems=view!!.findViewById(R.id.rvFavItemsDisplay)
        CoroutineScope(Dispatchers.IO).launch{
            val repository = WishListRepository()
            val response= repository.getUserFavItems()
            val lst= response.data
            RoomDb.getInstance(requireContext()).getFavInfo().insertFavItems(lst!!)
            withContext(Dispatchers.Main){
                val adapter = FavouriteAdapter(lst as ArrayList<FavouriteItem>,requireContext())
                rvDisplayFavItems.adapter=adapter
                rvDisplayFavItems.layoutManager= LinearLayoutManager(context)
            }
        }
        return view
    }


}