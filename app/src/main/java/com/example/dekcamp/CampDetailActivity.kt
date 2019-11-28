package com.example.dekcamp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.dekcamp.data.Camp
import kotlinx.android.synthetic.main.activity_camp_detail.*

class CampDetailActivity : AppCompatActivity() {

    var state = false
    var stateRegis = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camp_detail)
    }

    fun onClickedVote(view: View) {
        state=!state
        if (state) {
            button.setBackgroundColor(Color.parseColor("#FFC400"))
            button.setTextColor(Color.parseColor("#FF0030"))
        } else {
            button.setBackgroundColor(Color.parseColor("#E7D2D2"))
            button.setTextColor(Color.BLACK)
        }
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



    fun setComponentCamp(camp: Camp) {
        campTextView.text = camp.campType
        voteTextView.text = "มีคนโหวตให้แล้ว " + camp.vote.toString() + " คน"
        descriptionTextView.text = camp.detail
        campdayTextView.text = camp.campStrat.toString() + "-" + camp.campEnd.toString() + "( " + camp.durationType + " )"
        amountTextView.text = camp.amountPeople.toString() + " คน"
        oldTextView.text = "อายุ " + camp.minAge.toString() + " ปีขึ้นไป"
        placeTextView.text = camp.address
        costTextView.text = camp.price.toString() + " บาท ( " + camp.payWhen + " )"
        if (camp.certificate)
            certificateTextView.text = "มี"
        else
            certificateTextView.text = "ไม่มี"
        closeTextView.text = camp.endRegis.toString()
        contactTextView.text = camp.contact
    }
}

