package com.example.myapplication


import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication.R.layout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var data: ArrayList<String> = ArrayList()
    lateinit var mLayoutManager: LinearLayoutManager
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        addData()
        setAdapter("Vertical List")
        initListener()


    }

    private fun initListener() {
        radio_grp.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                radio1.id -> setAdapter("Vertical List")
                radio2.id -> setAdapter("Horizontal List")
                radio3.id -> setAdapter("Grid View with 2 colomns")
                radio4.id -> setAdapter("Staggered View")


            }

        })
        zoomedImage.setOnClickListener {
            zoomedImage.visibility = View.GONE
            view_back.visibility = View.GONE
        }
    }

    private fun setAdapter(s: String) {

        if (s.equals("Vertical List")) {
            label2.text = s + "- Swipe Up/Down"
            mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            recycler_view_image.layoutManager = mLayoutManager
        } else if (s.equals("Horizontal List")) {
            label2.text = s + "- Swipe Left/Right"
            mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            recycler_view_image.layoutManager = mLayoutManager
        } else if (s.equals("Grid View with 2 colomns")) {
            label2.text = s + "- Swipe Up/Down"
            gridLayoutManager = GridLayoutManager(applicationContext, 2)
            recycler_view_image.layoutManager = gridLayoutManager
        } else if (s.equals("Staggered View")) {
            label2.text = s + "- Swipe Up/Down"
            staggeredGridLayoutManager =
                StaggeredGridLayoutManager(4, LinearLayoutManager.VERTICAL)
            recycler_view_image.layoutManager = staggeredGridLayoutManager
        }
        var adapter = Adapter(this, data, zoomedImage, view_back)
        recycler_view_image.adapter = adapter
    }

    private fun addData() {
        data.add("https://therightsofnature.org/wp-content/uploads/2018/01/turkey-3048299_1920.jpg")
        data.add("https://scx1.b-cdn.net/csz/news/800/2019/2-nature.jpg")
        data.add("https://i0.wp.com/cdn-prod.medicalnewstoday.com/content/images/articles/325/325466/man-walking-dog.jpg?w=1155&h=1541")
        data.add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/nature-quotes-1557340276.jpg")
        data.add("https://isha.sadhguru.org/blog/wp-content/uploads/2016/05/natures-temples.jpg")
        data.add("https://www.afd.fr/sites/afd/files/styles/visuel_principal/public/2019-01-07-16-19/mangrove%20couv.jpg?itok=IGNV-r8C")
        data.add("https://media.npr.org/assets/img/2018/04/04/gettyimages-937327264_wide-e4ed916de3b7fd7490e2661773e5ae5cde4598b0.jpg?s=1400")
        data.add("https://lh3.googleusercontent.com/proxy/nle0R93yrAC0IvnvPVLKlR-Fvdc5IOTHihUFARheiv2Aovq8NCsbGWOKppVyjwbPzf021ZsCXLLXW4YUNArz2I5CWxbyWpXJz7GxTunvR-KiHHXBL3uRdW6hN3M")
        data.add("https://www.cisl.cam.ac.uk/news/images/business-for-nature.jpg")
        data.add("https://cdn.climatechangenews.com/files/2019/12/09161458/adventure-awesome-boardwalk-726298.jpg")

    }

}


