package com.example.dekcamp.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.dekcamp.R
import com.example.dekcamp.data.Util
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_test.*

class ProfileFragment : Fragment() {
    val mRef = FirebaseDatabase.getInstance().reference


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        val name = root.findViewById<TextView>(R.id.name)
        val img = root.findViewById<ImageView>(R.id.img)

        val user = Util.currentUser

        img.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 0)
        }

        user.observe(this, Observer {

            name.text = "${it.firstname} ${it.lastname}"

            if (it.image == "") {
                Picasso.with(context!!).load(R.drawable.shrimp).into(img)
            } else {
                Log.i("imageOn", "loading ${it.image}")
                val err: Drawable =
                    ContextCompat.getDrawable(context!!, R.drawable.ic_error_black_24dp)!!
                DrawableCompat.setTint(
                    DrawableCompat.wrap(err),
                    ContextCompat.getColor(context!!, R.color.colorAccent)
                )
                Picasso.with(context!!).load(it.image)
                    .transform(CropCircleTransformation()).error(err).into(img)
            }
        })

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            val uri = data!!.data
            Log.i("getImage", uri.toString())

            if (uri != null) {
                val user = Util.currentUser.value!!
                val storeRef = FirebaseStorage.getInstance().reference
                val ref = storeRef.child("images/${user.student_id}/profile")

                ref.putFile(uri).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        val err: Drawable =
                            ContextCompat.getDrawable(context!!, R.drawable.ic_error_black_24dp)!!

                        Picasso.with(context!!).load(uri.toString())
                            .transform(CropCircleTransformation()).error(err).into(img)
                        val link = uri.toString()
                        Log.i("getImage", "success")

                        val user = Util.currentUser.value!!
                        Log.i("getImage", "id: ${user.user_id}")
                        mRef.child("users").child(user.user_id).child("image").setValue(link)
                    }
                }
            }
        }
    }
}