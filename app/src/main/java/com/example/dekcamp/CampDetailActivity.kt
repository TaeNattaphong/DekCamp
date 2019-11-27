package com.example.dekcamp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class CampDetailActivity : AppCompatActivity() {

    lateinit var voteButton: Button
    var state: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camp_detail)

        voteButton = findViewById(R.id.voteButton)
    }

    fun onClickedVote(view: View) {
        state=!state
        if (state) {
            voteButton.setBackgroundColor(Color.parseColor("#FFC200"))
            voteButton.setTextColor(Color.parseColor("#E60000"))
            //vote ++

        } else {
            voteButton.setBackgroundColor(Color.parseColor("#E0D9D9"))
            voteButton.setTextColor(Color.BLACK)
        }



        //#E0D9D9  #FFC200

    }
}
