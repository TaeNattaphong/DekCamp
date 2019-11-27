package com.example.dekcamp

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Spinner
import com.example.dekcamp.data.Camp
import kotlinx.android.synthetic.main.activity_form_register_camp.*

class RegisterCampActivity : AppCompatActivity() {

    var campList = arrayOf("ค่ายแนะแนว", "ค่ายติว", "ค่ายพัฒนาทักษะ", "ค่ายภาษา", "ค่ายอนุรักษ์", "ค่ายอาสา", "ค่ายเด็กมหาลัย")
    var payList = arrayOf("ลงทะเบียน", "ผ่านการคัดเลือก", "-")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_register_camp)

        // Set the drop down view resource
        // Finally, data bind the spinner object with dapter
        val spinner = findViewById<Spinner>(R.id.paySpinner)
        val spinner2 = findViewById<Spinner>(R.id.spinnerCamp)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, payList)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, campList)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter = adapter
        spinner2.adapter = adapter2

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

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        fromButton.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    dateTV1.setText("" + dayOfMonth + "/" + month + "/" + year)
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        toButton.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    dateTV2.setText("" + dayOfMonth + "/" + month + "/" + year)
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        closeButton.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    dateTV3.setText("" + dayOfMonth + "/" + month + "/" + year)
                },
                year,
                month,
                day
            )
            dpd.show()
        }
    }

    fun onClickedRegisterCamp(view: View) {
        val camp_id = ""
        val campname = nameCampEditText.text.toString()
        val detail = descriptionEditText.text.toString()
        val adress = placeEditText.text.toString()
        val contact = contactEditText.text.toString()
        val price = costEditText.text.toString().toInt()
        val certificate = haveOrNothave.text.toString()
        val maxPeople = amountEditText.text.toString().toInt()
        val minAge = oldEditText.text.toString().toInt()
        val campStrat = dateTV1.text.toString()
        val campEnd = dateTV2.text.toString()
        val startRegis = ""
        val endRegis = dateTV3.text.toString()
        val vote = 0
        val typeCamp = standOrGoback.text.toString()
        val amountPeople = 0
        val payWhen = paySpinner.selectedItem.toString()
        val camp = spinnerCamp.selectedItem.toString()

        //create object camp
        val camping = Camp(camp_id,campname,detail,adress,contact,price,certificate,maxPeople,minAge,campStrat,campEnd,startRegis,endRegis,vote,typeCamp,amountPeople,payWhen,camp)
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.standRadioButton ->
                    if (checked) {
                        standOrGoback.setText("ค้างคืน")
                    }
                R.id.gobackRadioButton ->
                    if (checked) {
                        standOrGoback.setText("ไป-กลับ")
                    }
                R.id.haveRadioButton ->
                    if (checked) {
                        haveOrNothave.setText("มี")
                    }
                R.id.nothaveRadioButton ->
                    if (checked) {
                        haveOrNothave.setText("ไม่มี")
                    }
            }
        }
    }


}