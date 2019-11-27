package com.example.dekcamp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.dekcamp.data.User
import kotlinx.android.synthetic.main.activity_form_register.*
import com.example.dekcamp.data.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private var generList = arrayOf("Male", "Female")
    private val mRef = FirebaseDatabase.getInstance().reference
    private val mAuth = FirebaseAuth.getInstance()


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_register)

        // Set the drop down view resource
        // Finally, data bind the spinner object with dapter
        val spinner = genderSpinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, generList)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter = adapter


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        pickDateBtn.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    c.set(year, month, dayOfMonth)
                    dateTv.text = Util.DATE_FORMAT.format(c.time)
                },
                year,
                month,
                day
            )
            dpd.show()
        }

    }


    fun onClickedBack(view: View) {
        finish()
    }

    fun onClickedRegisterInRegisterPage(view: View) {

        if (dateTv.text.toString() == "dd/mm/yyyy") {
            Toast.makeText(this, "กรุณาใส่วันเกิดให้เรียบร้อย", Toast.LENGTH_LONG).show()
            return
        }

        val firstname = firstnameEditText.text.toString()
        val lastname = lastnameEditText.text.toString()
        val nickName = nicknameEditText.text.toString()
        val student_id = studentIdEditText.text.toString()
        val gender = genderSpinner.selectedItem.toString()
        val brithday = Util.DATE_FORMAT.parse(dateTv.text.toString())!!.time
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val password2 = passwordEditText2.text.toString()
        val phone = phoneEditText.text.toString()
        val allegic = allegicEditText.text.toString()

        val arrEditText = arrayListOf(
            firstnameEditText,
            lastnameEditText,
            nicknameEditText,
            studentIdEditText,
            emailEditText,
            phoneEditText
        )

        if (brithday >= System.currentTimeMillis()) {
            Toast.makeText(this, "วันเกิดต้องน้อยกว่าวลาปัจจุบัน", Toast.LENGTH_LONG).show()
            return
        }

        arrEditText.forEach {
            if (it.text.toString() == "") {
                Toast.makeText(this, "กรุณากรอกชื่อ-นามสกุล ชื่อเล่น รหัสนิสิต email และเบอร์โทรศัพท์ให้เรียบร้อย", Toast.LENGTH_LONG).show()
                return
            }
            if (it.text.contains(Regex("[&=_'${'-'}+,<>${'{'}${'}'}{}.!@]"))) {
            Toast.makeText(this, "ไม่สารถใช้ตัวอักษร & = ${'_'} - + < > ${'['} ${']'} { } . ! @ ในชื่อได้", Toast.LENGTH_LONG).show()
            return
        }

//            if (it.toString().contains(Regex("[${Regex.escape("&=_-+<>][}{!@")}]"))) {
//                Toast.makeText(this, "ไม่สารถใช้ตัวอักษร & = ${'_'} - + < > ${'['} ${']'} { } . ! @ ในชื่อได้", Toast.LENGTH_LONG).show()
//                return
//            }
        }

        if (allegic.contains(Regex("[&=_'${'-'}+,<>${'{'}${'}'}{}.!@]"))) {
            Toast.makeText(this, "ไม่สารถใช้ตัวอักษร & = ${'_'} - + < > ${'['} ${']'} { } . ! @ ในชื่อได้", Toast.LENGTH_LONG).show()
            return
        }

        if (password != password2) {
            Toast.makeText(this, "password ทั้ง 2 ไม่ถูกต้อง", Toast.LENGTH_LONG).show()
            return
        }
        if (password.length <= 5) {
            Toast.makeText(this, "จำนวน password ต้องมากกว่า 5 ตัวขึ้นไป", Toast.LENGTH_LONG).show()
            return
        }
        if (phone.length != 10) {
            Toast.makeText(this, "กรุณากรอกเบอร์โทรศัพท์ให้ถูกต้อง", Toast.LENGTH_LONG).show()
            return
        }


        val candidate =
            User(firstname, lastname, nickName, student_id, gender, brithday, "$email@ku.th", phone, "")
        Log.i("registery", candidate.toString())

        registerFirebase(candidate.email, password, candidate)
    }

    private fun registerFirebase(email: String, pass: String, newUser: User) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.i("registery", "success")

                // create data on firebase
                val user = task.result!!.user!!.uid
                Log.i("registery", "user_id $user")
                newUser.user_id = user
                mRef.child("users").child(user).setValue(newUser)

                Toast.makeText(
                    applicationContext,
                    "คุณ ${newUser.firstname} ${newUser.lastname} ถูกเพิ่มเข้าสู่ระบบแล้ว",
                    Toast.LENGTH_LONG
                ).show()

                Util.currentUser.value = newUser


                val intent = Intent(this, LoadingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                val e = task.exception as FirebaseAuthException
                Toast.makeText(this, "Failed Registration: " + e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
