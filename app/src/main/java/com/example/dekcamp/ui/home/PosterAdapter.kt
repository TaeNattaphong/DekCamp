package com.example.dekcamp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.dekcamp.R
import com.example.dekcamp.data.Camp
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class PosterAdapter(val arr: MutableLiveData<ArrayList<Camp>>) :
    RecyclerView.Adapter<PosterAdapter.ViewHolder>() {

    lateinit var context: Context

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {

        val camp = arr.value!![i]
        val img = holder.getImg
        val name = holder.getName
        val radius = 100
        val margin = 0
        val trans = RoundedCornersTransformation(radius, margin)
        Picasso.with(context).load(camp.image).transform(trans).into(img)
        name.text = camp.campname

    }

    override fun getItemCount(): Int {
        return arr.value!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_poster, parent, false)
        context = parent.context

        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val getImg = view.findViewById<ImageView>(R.id.img)
        val getName = view.findViewById<TextView>(R.id.img_name)
    }
}