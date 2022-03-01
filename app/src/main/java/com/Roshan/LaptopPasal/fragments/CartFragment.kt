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
import com.dipesh.onlinegadgetsstore.database.RoomDb
import com.dipesh.onlinegadgetsstore.entity.Cart
import com.dipesh.onlinegadgetsstore.repository.CartRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CartFragment : Fragment() {
    private lateinit var rvDisplayCartItems: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        rvDisplayCartItems=view!!.findViewById(R.id.rvCartItemsDisplay)
        CoroutineScope(Dispatchers.IO).launch{
            val repository = CartRepository()
            val response= repository.getUserCartItems()
            val lst= response.data
            RoomDb.getInstance(requireContext()).getCartInfo().insertCart(lst!!)
            withContext(Dispatchers.Main){
                val adapter = CartAdapter(lst as ArrayList<Cart>,requireContext())
                rvDisplayCartItems.adapter=adapter
                rvDisplayCartItems.layoutManager=LinearLayoutManager(context)
            }
        }
        return view
    }


}