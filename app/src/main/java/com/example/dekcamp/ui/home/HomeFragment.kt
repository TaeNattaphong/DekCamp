package com.example.dekcamp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dekcamp.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val r1 = root.findViewById<RecyclerView>(R.id.poster_recycler)
        r1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        r1.adapter = PosterAdapter()


        val lb = root.findViewById<LinearLayout>(R.id.button_layout)
        val headers = arrayListOf("อาสา", "พัฒนา", "อนุรักษ์", "วิทยาศาสตร์", "สังคม", "ชนบท", "ศาสนา", "ต่างประเทศ", "วิชาการ")
        headers.forEach {
            val btn = Button(context)
            btn.text = it
            btn.setPadding(0, 0, 10, 0)
            lb.addView(btn)
        }

        val r2 = root.findViewById<RecyclerView>(R.id.display_recycler)
        r2.layoutManager = LinearLayoutManager(context)
        r2.adapter = PosterDisplayAdapter()


        return root
    }

}