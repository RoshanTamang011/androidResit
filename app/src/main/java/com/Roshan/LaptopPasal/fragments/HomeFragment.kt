package com.dipesh.onlinegadgetsstore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dipesh.onlinegadgetsstore.R
import com.dipesh.onlinegadgetsstore.adapter.DiscoverItemAdapter
import com.dipesh.onlinegadgetsstore.database.RoomDb
import com.dipesh.onlinegadgetsstore.entity.DiscoverItem
import com.dipesh.onlinegadgetsstore.model.DiscoverItemModel
import com.dipesh.onlinegadgetsstore.repository.DiscoverItemRepository
import com.smarteist.autoimageslider.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var sliderLayout: SliderLayout
    private lateinit var rvProducts: RecyclerView
    private var listProducts=ArrayList<DiscoverItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        //Image slider
        sliderLayout=view.findViewById(R.id.imgSlider)
        rvProducts=view.findViewById(R.id.rvProducts)
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL)
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderLayout.setScrollTimeInSec(1)
        setSliderViews()

        CoroutineScope(Dispatchers.IO).launch {
            val repository= DiscoverItemRepository()
            val response = repository.getProducts()
            val lst =response.data
            RoomDb.getInstance(requireContext()).getProductInfo().insertProduct(lst as List<DiscoverItem>)
            withContext(Dispatchers.Main){

                val productAdapter=DiscoverItemAdapter(lst as ArrayList<DiscoverItem>,context!!)
                rvProducts.layoutManager=GridLayoutManager(context,2)
                rvProducts.adapter=productAdapter
            }
        }

        return view
    }


    private fun setSliderViews() {
        for(i in 0..4){
            val sliderView = DefaultSliderView(context)
            when(i){
                0 ->{
                    sliderView.imageUrl="data:image/jpeg;base64,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"
                }

                1 ->{
                    sliderView.imageUrl="https://www.gizbot.com/img/2020/12/amazon-upto-30-off-on-laptops-1607314744.jpg"
                }
                2 ->{
                    sliderView.imageUrl="https://hotdealszone.com/wp-content/uploads/2019/05/Amazon-Offers-on-best-selling-laptops.jpg"
                }
                3 ->{
                    sliderView.imageUrl="https://pcwnepal.com.np/wp-content/uploads/2019/09/dashain-offer1.jpg"
                }
                4 ->{
                    sliderView.imageUrl="https://techlekh.com/wp-content/uploads/2017/09/neo-store-offer.jpg"
                }
            }
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            sliderLayout.addSliderView(sliderView)
        }
    }
}