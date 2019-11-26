package com.example.dekcamp

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.dekcamp.data.Gender
import com.example.dekcamp.data.User
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


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
        //gender ยังไม่เสร็จ
        val gender = genderSpinner.getSelectedItem().toString()
        val brithday = dateTv.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val phone = phoneEditText.text.toString()
        //object of candidate
        val candidate = User(fristname, lastname, student_id, Gender.Male, brithday, email, password, phone)
        finish()
    }
}
