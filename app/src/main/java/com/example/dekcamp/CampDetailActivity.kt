package com.example.dekcamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dekcamp.data.Camp

class CampDetailActivity : AppCompatActivity() {

    val campTextView = findViewById<TextView>(R.id.campTextView)
    val voteTV = findViewById<TextView>(R.id.voteTextView)
    val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
    val campdayTextView = findViewById<TextView>(R.id.campdayTextView)
    val amountTextView = findViewById<TextView>(R.id.amountTextView)
    val oldTextView = findViewById<TextView>(R.id.oldTextView)
    val placeTextView = findViewById<TextView>(R.id.placeTextView)
    val costTextView = findViewById<TextView>(R.id.costTextView)
    val certificateTextView = findViewById<TextView>(R.id.certificateTextView)
    val closeTextView = findViewById<TextView>(R.id.closeTextView)
    val contactTextView = findViewById<TextView>(R.id.contactTextView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camp_detail)
    }

    fun setComponentCamp(camp: Camp) {
        campTextView.text = camp.campType
        voteTV.text = "มีคนโหวตให้แล้ว" + camp.vote.toString() + "คน"
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

