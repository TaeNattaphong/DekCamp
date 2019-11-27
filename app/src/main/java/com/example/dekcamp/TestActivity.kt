package com.example.dekcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_test.*
import java.io.File

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        val btn = btn

        btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 0)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            val uri = data!!.data
            Log.i("getImage", uri.toString())

            if (uri != null) {
                val storeRef = FirebaseStorage.getInstance().reference
//                    .child("images/1").downloadUrl.addOnSuccessListener {
//                    Picasso.with(this).load(it.toString()).into(img)
//                    Log.i("getImage", "success")
//                }
                val ref = storeRef.child("images/1")
//                val img = img
//
                ref.putFile(uri).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {  uri ->
                        Picasso.with(this).load(uri.toString()).into(img)
                        Log.i("getImage", "success")
                    }
                }
//                    val imgURL = it.metadata!!.reference.toString()
//                    Log.i("getImage", "img url: $imgURL")
//
//                    val b = MediaStore.Images.Media.getBitmap(this.contentResolver)
//
//                }
            }
        }
    }
}
