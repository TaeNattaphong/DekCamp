package com.example.dekcamp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.dekcamp.data.Camp
import com.example.dekcamp.data.Util
import kotlinx.android.synthetic.main.activity_form_register_camp.*

class RegisterCampActivity : AppCompatActivity() {

    private var campType = Util.campType
    private var payList = arrayOf("ลงทะเบียน", "ผ่านการคัดเลือก", "-")

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_register_camp)

        // Set the drop down view resource
        // Finally, data bind the spinner object with dapter
        val spinner = paySpinner
        val spinner2 = spinnerCamp
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, payList)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, campType)

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        spinner.adapter = adapter
        spinner2.adapter = adapter2

        // Set an on item selected listener for spinner object
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View,
//                position: Int,
//                id: Long
//            ) {
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {}
//
//        }

//        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View,
//                position: Int,
//                id: Long
//            ) {
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {}
//
//        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        fromButton.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    dateTV1.text = ("$dayOfMonth/$month/$year")
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
                    dateTV2.text = ("$dayOfMonth/$month/$year")
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
                    dateTV3.text = ("$dayOfMonth/$month/$year")
                },
                year,
                month,
                day
            )
            dpd.show()
        }
    }

    fun onClickedRegisterCamp(view: View) {
        val campId = ""
        val campName = nameCampEditText.text.toString()
        val detail = descriptionEditText.text.toString()
        val address = placeEditText.text.toString()
        val contact = contactEditText.text.toString()
        val price = costEditText.text.toString().toDouble()
        val certificate = isCerficate.text.toString()
        val maxPeople = amountEditText.text.toString().toInt()
        val minAge = ageEditText.text.toString().toInt()
        val campStart = dateTV1.text.toString()
        val campEnd = dateTV2.text.toString()
        val endRegis = dateTV3.text.toString()
        val typeCamp = standOrGoback.text.toString()
        val payWhen = paySpinner.selectedItem.toString()
        val camp = spinnerCamp.selectedItem.toString()

        val arrEditText = arrayListOf<EditText>(
            nameCampEditText,
            descriptionEditText,
            placeEditText,
            contactEditText,
            costEditText,
            amountEditText,
            ageEditText
        )

        val arrTextView = arrayListOf<TextView>(
            dateTV1,
            dateTV2,
            dateTV3
        )

        val text = arrayListOf("ชื่อค่าย", "สถานที่จัด", "ช่องทางติดต่อ")
        arrayListOf(0, 2, 3).forEachIndexed { i, it ->
            if (arrEditText[it].text.toString() == "") {
                Toast.makeText(this, "กรุณากรอก ${text[i]} ให้ครบถ้วน", Toast.LENGTH_LONG).show()
                return@onClickedRegisterCamp
            }
        }
        
        arrTextView.forEach {
            if (it.text == "dd/mm/yyyy") {
                Toast.makeText(this, "กรุณาใส่วันที่ให้ครบถ้วน", Toast.LENGTH_LONG).show()
                return@onClickedRegisterCamp
            }
        }

        val campStartVal = Util.DATE_FORMAT.parse(campStart)!!.time
        val campEndVal = Util.DATE_FORMAT.parse(campEnd)!!.time
        val endRegisVal = Util.DATE_FORMAT.parse(endRegis)!!.time

        arrayListOf(campStartVal, campEndVal, endRegisVal).forEach {
            if (it <= System.currentTimeMillis()) {
                Toast.makeText(this, "เวลาไม่สามารถน้อยกว่าเวลาปัจจุบัน", Toast.LENGTH_LONG).show()
                return@onClickedRegisterCamp
            }
        }

        if (campStartVal >= campEndVal) {
            Toast.makeText(this, "วันที่จัดตั้งไม่สามารถมากกว่าวันที่สิ้นสุด", Toast.LENGTH_LONG).show()
            return
        }

        if (certificate == "") {
            Toast.makeText(this, "กรุณาเลือกสถานะใบรับรอง", Toast.LENGTH_LONG).show()
            return
        }

        //create object camp
        val newCamp = Camp(
            campId,
            campName,
            detail,
            address,
            contact,
            price,
            certificate == "มี",
            maxPeople,
            minAge,
            campStartVal,
            campEndVal,
            System.currentTimeMillis(),
            endRegisVal,
            0,
            typeCamp,
            0,
            payWhen,
            camp,
            true
        )


    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.standRadioButton ->
                    if (checked) {
                        standOrGoback.text = "ค้างคืน"
                    }
                R.id.gobackRadioButton ->
                    if (checked) {
                        standOrGoback.text = "ไป-กลับ"
                    }
                R.id.haveRadioButton ->
                    if (checked) {
                        isCerficate.text = "มี"
                    }
                R.id.nothaveRadioButton ->
                    if (checked) {
                        isCerficate.text = "ไม่มี"
                    }
            }
        }
    }

    fun cannotNull() {

    }


}