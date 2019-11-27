package com.example.dekcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.dekcamp.data.User
import com.example.dekcamp.data.Util
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var userFirebase: DatabaseReference
    private val listener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {}
        override fun onDataChange(p0: DataSnapshot) {
            Util.currentUser.value = p0.getValue(User::class.java)!!
        }
    }

    private lateinit var username: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var login: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = inputEmail
        password = inputPassword
        login = loginButton
        init()
    }

    fun onClickedRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun init() {
        val liveUser = MutableLiveData<String>()
        val livePass = MutableLiveData<String>()

        username.editText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                liveUser.value = p0.toString().trim()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        password.editText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                livePass.value = p0.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        var userValid = false
        var passValid = false
        liveUser.observe(this, Observer<String> {
            userValid = false
            if (it == "") username.error = "กรุณาใส่ Email"
            else {
                username.error = null
                userValid = true
            }
            checkButton(userValid, passValid)
        })
        livePass.observe(this, Observer<String> {
            passValid = false
            when {
                it == "" -> password.error = "กรุณาใส่ password"
                it.length <= 5 -> password.error = "password ต้องมีมากกว่า 5 ตัวอักษรขึ้นไป"
                else -> {
                    password.error = null
                    passValid = true
                }
            }
            checkButton(userValid, passValid)
        })

        login.setOnClickListener {
            mAuth.signInWithEmailAndPassword(liveUser.value!!, livePass.value!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = task.result!!.user!!.uid
                        userFirebase = FirebaseDatabase.getInstance().getReference("users")
                        userFirebase.child(user).addValueEventListener(listener)

                        val intent = Intent(this, SidebarActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Username หรือ Password ของท่านไม่ถูกต้อง",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }


    }

    private fun checkButton(userValid: Boolean, passValid: Boolean) {
        login.isEnabled = (userValid && passValid)
    }

}
