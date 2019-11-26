package com.example.dekcamp.ui.send

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dekcamp.LoginActivity
import com.example.dekcamp.R
import com.example.dekcamp.data.User
import com.example.dekcamp.data.Util
import com.google.firebase.auth.FirebaseAuth

class SendFragment : Fragment() {

    private lateinit var sendViewModel: SendViewModel
    private val mAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        mAuth.signOut()
        Util.currentUser = User()

        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return null
    }
}