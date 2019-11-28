package com.example.dekcamp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dekcamp.R
import com.example.dekcamp.data.Camp
import com.example.dekcamp.data.Util
import com.example.dekcamp.ui.home.PosterAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private val mRef = FirebaseDatabase.getInstance().reference
    lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val arr = MutableLiveData<ArrayList<Camp>>()
        arr.value = ArrayList()
        val tmp = ArrayList<Camp>()
        val desc = ArrayList<String>()
        val index = (arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)!!

        mRef.child("camps").orderByChild("campType").equalTo(Util.lastSelected).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                tmp.clear()
                desc.clear()
                p0.children.forEach { tmp.add(it.getValue(Camp::class.java)!!) }

                if (index == 1) {
                    tmp.sortWith(Comparator { a, b ->
                        a.startRegis.compareTo(b.startRegis)
                    })
                    tmp.forEach { desc.add("ลงวันที่ : ${Util.DATE_FORMAT.format(Date(it.startRegis))}") }
                }
                else if (index == 2) {
                    tmp.sortWith(Comparator { a, b ->
                        b.vote.compareTo(a.vote)
                    })
                    tmp.forEach { desc.add("จำนวน Vote : ${it.vote}") }
                }
                else if (index == 3) {
                    tmp.sortWith(Comparator { a, b ->

                        val timeA = a.endRegis - a.startRegis
                        val timeB = b.endRegis - b.startRegis
                        timeB.compareTo(timeA)
                    })
                    tmp.forEach {
                        val s = SimpleDateFormat("dd")
                        val delta = s.format(Date(it.endRegis - it.startRegis))
                        desc.add("เหลือเวลาอีก : $delta วัน")
                    }
                }

                arr.value = tmp

            }
        })

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_display)



        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = ShowAdapter(arr, desc)

        arr.observe(this, Observer {
            recycler.adapter!!.notifyDataSetChanged()
        })





        return root
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
////                val intent = Intent(this, MainActivity::class.java)
////                startActivity(intent)
//                activity!!.finish()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}