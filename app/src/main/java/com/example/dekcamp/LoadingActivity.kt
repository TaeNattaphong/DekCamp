package com.example.dekcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dekcamp.data.User
import com.example.dekcamp.data.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoadingActivity : AppCompatActivity() {

    // login firebase variable
    private val mAuth = FirebaseAuth.getInstance()
    private val mAuthListener = FirebaseAuth.AuthStateListener {
        val user: FirebaseUser? = it.currentUser
        // if already login, login to app, if not, let's login
        if (user != null) {
            // get data from db
            val mRef = FirebaseDatabase.getInstance().getReference("users").child(user.uid)
            Log.i("registery", "login in")
            Log.i("registery", user.uid)
            Util.currentUser.value = User()
            mRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    Util.currentUser.value = p0.getValue(User::class.java)!!
                }
            })
            val intent = Intent(this, SidebarActivity::class.java)
            startActivity(intent)
            finish()
        }
        else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_wait)
    }
}
