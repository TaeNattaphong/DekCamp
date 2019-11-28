package com.example.dekcamp

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.dekcamp.data.Camp
import com.example.dekcamp.data.Util
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_camp_detail.*

class CampDetailActivity : AppCompatActivity() {

    var state = false
    var stateRegis = false
    val mRef = FirebaseDatabase.getInstance().reference
    lateinit var camp: Camp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camp_detail)

        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        val data = intent.getStringExtra("camp")!!
        camp = Gson().fromJson(data, Camp::class.java)

        setComponentCamp(camp)
    }

    fun onClickedVote(view: View) {
        state=!state
        var del = 0
        if (state) {
            button.setBackgroundColor(Color.parseColor("#FFC400"))
            button.setTextColor(Color.parseColor("#FF0030"))
            del = 1
        } else {
            button.setBackgroundColor(Color.parseColor("#E7D2D2"))
            button.setTextColor(Color.BLACK)
            del = -1
        }

        camp.vote += del
        mRef.child("camps").child(camp.camp_id).child("vote").setValue(camp.vote)
    }

    fun onClickedRegis(view: View) {
        stateRegis=!stateRegis
        if (stateRegis) {
            regisButton.setBackgroundColor(Color.parseColor("#00FF83"))
            regisButton.text = "สมัครแล้ว!!!"
        } else {
            regisButton.setBackgroundColor(Color.parseColor("#FF005A"))
            regisButton.text = "สมัครเลย???"
        }


    }



    @SuppressLint("SetTextI18n")
    fun setComponentCamp(camp: Camp) {
        Picasso.with(applicationContext).load(camp.image).into(imageCamp)
        campTextView.text = camp.campType
        voteTextView.text = "มีคนโหวตให้แล้ว " + camp.vote.toString() + " คน"
        descriptionTextView.text = camp.detail
        campdayTextView.text = Util.DATE_FORMAT.format(camp.campStrat) + "-" + Util.DATE_FORMAT.format(camp.campEnd) + "( " + camp.durationType + " )"
        amountTextView.text = camp.amountPeople.toString() + " คน"
        oldTextView.text = "อายุ " + camp.minAge.toString() + " ปีขึ้นไป"
        placeTextView.text = camp.address
        costTextView.text = camp.price.toString() + " บาท ( " + camp.payWhen + " )"
        if (camp.certificate)
            certificateTextView.text = "มี"
        else
            certificateTextView.text = "ไม่มี"
        closeTextView.text = Util.DATE_FORMAT.format(camp.endRegis)
        contactTextView.text = camp.contact
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->  {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

