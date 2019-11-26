package com.example.dekcamp.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dekcamp.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class PosterDisplayAdapter() :
    RecyclerView.Adapter<PosterDisplayAdapter.ViewHolder>() {

    lateinit var context: Context

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val url = "https://scontent.fbkk10-1.fna.fbcdn.net/v/t1.0-1/c2.0.1436.1436a/68267623_1373380349487136_71373787999764480_o.jpg?_nc_cat=101&_nc_ohc=0bFSLGpyEXcAQm9_3c7eb08-k7NSU5O1c_zrw9yFsCpUTNWUV0dACj05g&_nc_ht=scontent.fbkk10-1.fna&oh=391bb2042dcf1059d0078e837f04a08d&oe=5E801E24"

        val img = holder.getImg
        val text = holder.getDesc
        val radius = 100
        val margin = 0
        val trans = RoundedCornersTransformation(radius, margin)
        Picasso.with(context).load(url).transform(trans).into(img)
        text.text = "1/10"

    }

    override fun getItemCount(): Int {
        return 10
}
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.poster_display, parent, false)
        context = parent.context

        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val getImg = view.findViewById<ImageView>(R.id.img)
        val getDesc = view.findViewById<TextView>(R.id.desc)
    }
}