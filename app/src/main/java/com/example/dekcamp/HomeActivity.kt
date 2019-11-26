package com.example.dekcamp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dekcamp.home.PosterAdapter
import com.example.dekcamp.home.PosterDisplayAdapter
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val r1 = poster_recycler
        r1.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        r1.adapter = PosterAdapter()


        val lb = button_layout
        val headers = arrayListOf("อาสา", "พัฒนา", "อนุรักษ์", "วิทยาศาสตร์", "สังคม", "ชนบท", "ศาสนา", "ต่างประเทศ", "วิชาการ")
        headers.forEach {
            val btn = Button(applicationContext)
            btn.text = it
            btn.setPadding(0, 0, 10, 0)
            lb.addView(btn)
        }

        val r2 = display_recycler
        r2.layoutManager = LinearLayoutManager(this)
        r2.adapter = PosterDisplayAdapter()


    }

    fun onClickedCamp(view: View) {
        val intent = Intent(this, CampActivity::class.java)

        startActivity(intent)
    }
}
