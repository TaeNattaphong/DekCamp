package com.example.dekcamp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dekcamp.CampDetailActivity
import com.example.dekcamp.QueryActivity
import com.example.dekcamp.R
import com.example.dekcamp.RecyclerMenuClickListener
import com.example.dekcamp.data.Camp
import com.example.dekcamp.data.Util
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private val mRef = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val arr = MutableLiveData<ArrayList<Camp>>()
        var tmpArr = ArrayList<Camp> ()
        arr.value = ArrayList()

        mRef.child("camps").limitToFirst(5).addValueEventListener(object :  ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                tmpArr.clear()
                p0.children.forEach { tmpArr.add(it.getValue(Camp::class.java)!!) }
                arr.value = tmpArr
            }
        })


        val r1 = root.findViewById<RecyclerView>(R.id.poster_recycler)
        r1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        r1.adapter = PosterAdapter(arr)
        r1.addOnItemTouchListener(
                RecyclerMenuClickListener(
                    context,
                    r1,
                    object : RecyclerMenuClickListener.OnItemClickListener {
                        override fun onItemClick(view: View?, position: Int) {
                            val intent = Intent(activity, CampDetailActivity::class.java)
                            intent.putExtra("camp", Gson().toJson(arr.value!![position]))
                            intent.putExtra("pos", "$position")
                            startActivity(intent)
                        }

                        override fun onLongItemClick(view: View?, position: Int) {}
                    }
                ))




        val lb = root.findViewById<LinearLayout>(R.id.button_layout)
        Util.campType.forEach {
            val btn = Button(context)
            btn.text = it
            btn.setPadding(0, 0, 10, 0)
            lb.addView(btn)
            btn.setOnClickListener {    _ ->
                Util.lastSelected = it
                val intent = Intent(activity, QueryActivity::class.java)
                intent.putExtra("name", it)
                startActivity(intent)
            }
        }

        val r2 = root.findViewById<RecyclerView>(R.id.display_recycler)
        r2.layoutManager = LinearLayoutManager(context)
        r2.adapter = PosterDisplayAdapter(arr)
        r2.addOnItemTouchListener(
            RecyclerMenuClickListener(
                context,
                r2,
                object : RecyclerMenuClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        val intent = Intent(activity, CampDetailActivity::class.java)
                        intent.putExtra("camp", Gson().toJson(arr.value!![position]))
                        intent.putExtra("pos", "$position")
                        startActivity(intent)
                    }

                    override fun onLongItemClick(view: View?, position: Int) {}
                }
            ))

        arr.observe(this, Observer {
            r1.adapter!!.notifyDataSetChanged()
            r2.adapter!!.notifyDataSetChanged()
        })

        return root
    }

}