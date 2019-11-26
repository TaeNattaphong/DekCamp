package com.example.dekcamp

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.dekcamp.data.User
import kotlinx.android.synthetic.main.activity_register.*
import java.util.logging.Logger

class RegisterActivity : AppCompatActivity() {

    var generList = arrayOf("Male", "Female")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Set the drop down view resource
        // Finally, data bind the spinner object with dapter
        val spinner = findViewById<Spinner>(R.id.genderSpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, generList)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter = adapter

        // Set an on item selected listener for spinner object
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        pickDateBtn.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateTv.setText(""+dayOfMonth+"/"+month+"/"+year)
            }, year, month, day)
            dpd.show()
        }

    }



    fun onClickedBack(view: View) {
        finish()
    }

    fun onClickedRegisterInRegisterPage(view: View) {
        val fristname = firstnameEditText.text.toString()
        val lastname = lastnameEditText.text.toString()
        val student_id = studentIdEditText.text.toString()
        val gender = genderSpinner.getSelectedItem().toString()
        val brithday = dateTv.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val phone = phoneEditText.text.toString()
        //object of candidate
        val candidate = User(fristname, lastname, student_id, gender, brithday, email, password, phone)
        finish()
    }
}
