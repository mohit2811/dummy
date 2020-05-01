package com.example.myapplication


import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.with
import org.w3c.dom.Text


class Adapter(
    var mContext: Activity, var imageList: ArrayList<String>,
    var imageView: ImageView,
    var viewBack: View
) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    private lateinit var animation: Animation
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.image_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.text.text=imageList.get(position)
        with(mContext)
            .load(imageList.get(position))
            .into(holder.image);
        holder.image.setOnLongClickListener(OnLongClickListener {

            animation = AnimationUtils.loadAnimation(
                mContext,
                R.anim.zoom_in
            )
            imageView.startAnimation(animation)
            with(mContext)
                .load(imageList.get(position))
                .into(imageView);
            imageView.visibility = View.VISIBLE
            viewBack.visibility = View.VISIBLE

            Toast.makeText(mContext, "Tap on Image to Zoom out", Toast.LENGTH_SHORT).show()
            false
        })
        holder.image.setOnClickListener(
            {
                val i = Intent(mContext, MapsActivity::class.java)
                mContext.startActivity(i)
            }
        )
    }


    override fun getItemCount(): Int {
        return imageList!!.size
    }

    open class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        lateinit var image: ImageView
        lateinit var text: TextView

        init {
            if (view != null) {
                image = view.findViewById(R.id.image)
                text = view.findViewById(R.id.text)
            }
        }
    }

}