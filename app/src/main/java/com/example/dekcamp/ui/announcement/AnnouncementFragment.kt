package com.example.dekcamp.ui.announcement

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.dekcamp.R
import com.example.dekcamp.data.Camp
import com.example.dekcamp.data.HaveCamp
import com.example.dekcamp.data.Util
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class AnnouncementFragment : Fragment() {

    private var campType = Util.campType
    private var payList = arrayOf("ลงทะเบียน", "ผ่านการคัดเลือก", "-")
    private val mRef = FirebaseDatabase.getInstance().reference
    private val storeRef = FirebaseStorage.getInstance().reference
    private lateinit var fileImagePreview: ImageView
    private lateinit var root: View
    private lateinit var link: Uri


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_announcement, container, false)

        // Set the drop down view resource
        // Finally, data bind the spinner object with dapter
        val spinner = root.findViewById<Spinner>(R.id.paySpinner)
        val spinner2 = root.findViewById<Spinner>(R.id.spinnerCamp)
        val adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, payList)
        val adapter2 = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, campType)

        Log.i("getValueOn", "$spinner")
        spinner.adapter = adapter
        spinner2.adapter = adapter2

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val fromButton = root.findViewById<Button>(R.id.fromButton)

        val dateTV1 = root.findViewById<TextView>(R.id.dateTV1)
        val dateTV2 = root.findViewById<TextView>(R.id.dateTV2)
        val dateTV3 = root.findViewById<TextView>(R.id.dateTV3)
        fromButton.setOnClickListener {
            val dpd = DatePickerDialog(
                context!!,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    c.set(year, month, dayOfMonth)
                    dateTV1.text = Util.DATE_FORMAT.format(c.time)
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        val toButton = root.findViewById<Button>(R.id.toButton)
        toButton.setOnClickListener {
            val dpd = DatePickerDialog(
                context!!,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    c.set(year, month, dayOfMonth)
                    dateTV2.text = Util.DATE_FORMAT.format(c.time)
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        val closeButton = root.findViewById<Button>(R.id.closeButton)
        closeButton.setOnClickListener {
            val dpd = DatePickerDialog(
                context!!,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    c.set(year, month, dayOfMonth)
                    dateTV3.text = Util.DATE_FORMAT.format(c.time)
                },
                year,
                month,
                day
            )
            dpd.show()
        }

        val standRadioButton = root.findViewById<RadioButton>(R.id.standRadioButton)
        standRadioButton.setOnClickListener { onRadioButtonClicked(standRadioButton) }

        val gobackRadioButton = root.findViewById<RadioButton>(R.id.gobackRadioButton)
        gobackRadioButton.setOnClickListener { onRadioButtonClicked(gobackRadioButton) }

        val haveRadioButton = root.findViewById<RadioButton>(R.id.haveRadioButton)
        haveRadioButton.setOnClickListener { onRadioButtonClicked(haveRadioButton) }

        val nothaveRadioButton = root.findViewById<RadioButton>(R.id.nothaveRadioButton)
        nothaveRadioButton.setOnClickListener { onRadioButtonClicked(nothaveRadioButton) }


        val selectButton = root.findViewById<Button>(R.id.selectButton)

        fileImagePreview = root.findViewById(R.id.fileImagePreview)
        selectButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 0)
        }

        val sendDataButton = root.findViewById<Button>(R.id.sendDataButton)
        sendDataButton.setOnClickListener { onClickedRegisterCamp() }

        return root
    }

    private fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked
            val standOrGoback = root.findViewById<TextView>(R.id.standOrGoback)
            val isCerficate = root.findViewById<TextView>(R.id.isCerficate)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {
            val uri = data!!.data
            Log.i("getImage", uri.toString())

            if (uri != null) {
//                val user = Util.currentUser.value!!
//                val storeRef = FirebaseStorage.getInstance().reference
//                Picasso.with(context!!).
//                val ref = storeRef.child("images").child("").child(user.user_id).child("profile")
//
////                ref.putFile(uri).addOnSuccessListener {
////                    ref.downloadUrl.addOnSuccessListener { uri ->
                val err: Drawable =
                    ContextCompat.getDrawable(context!!, R.drawable.ic_error_black_24dp)!!
//
                Picasso.with(context!!).load(uri.toString())
                    .transform(RoundedCornersTransformation(100, 0)).error(err)
                    .into(fileImagePreview)
                link = uri
//                    }
//                }
            }
        }
    }


    private fun onClickedRegisterCamp() {

        val nameCampEditText = root.findViewById<EditText>(R.id.nameCampEditText)
        val descriptionEditText = root.findViewById<EditText>(R.id.descriptionEditText)
        val placeEditText = root.findViewById<EditText>(R.id.placeEditText)
        val contactEditText = root.findViewById<EditText>(R.id.contactEditText)
        val costEditText = root.findViewById<EditText>(R.id.costEditText)
        val isCerficate = root.findViewById<TextView>(R.id.isCerficate)
        val amountEditText = root.findViewById<EditText>(R.id.amountEditText)
        val ageEditText = root.findViewById<EditText>(R.id.ageEditText)
        val dateTV1 = root.findViewById<TextView>(R.id.dateTV1)
        val dateTV2 = root.findViewById<TextView>(R.id.dateTV2)
        val dateTV3 = root.findViewById<TextView>(R.id.dateTV3)
        val standOrGoback = root.findViewById<TextView>(R.id.standOrGoback)
        val paySpinner = root.findViewById<Spinner>(R.id.paySpinner)
        val spinnerCamp = root.findViewById<Spinner>(R.id.spinnerCamp)

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
        val campType = spinnerCamp.selectedItem.toString()

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
                Toast.makeText(context!!, "กรุณากรอก ${text[i]} ให้ครบถ้วน", Toast.LENGTH_LONG)
                    .show()
                return@onClickedRegisterCamp
            }
        }

        arrTextView.forEach {
            if (it.text == "dd/mm/yyyy") {
                Toast.makeText(context!!, "กรุณาใส่วันที่ให้ครบถ้วน", Toast.LENGTH_LONG).show()
                return@onClickedRegisterCamp
            }
        }

        val campStartVal = Util.DATE_FORMAT.parse(campStart)!!.time
        val campEndVal = Util.DATE_FORMAT.parse(campEnd)!!.time
        val endRegisVal = Util.DATE_FORMAT.parse(endRegis)!!.time

        arrayListOf(campStartVal, campEndVal, endRegisVal).forEach {
            if (it <= System.currentTimeMillis()) {
                Toast.makeText(context!!, "เวลาไม่สามารถน้อยกว่าเวลาปัจจุบัน", Toast.LENGTH_LONG)
                    .show()
                return@onClickedRegisterCamp
            }
        }

        if (campStartVal >= campEndVal) {
            Toast.makeText(
                context!!,
                "วันที่จัดตั้งไม่สามารถมากกว่าวันที่สิ้นสุด",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (certificate == "") {
            Toast.makeText(context!!, "กรุณาเลือกสถานะใบรับรอง", Toast.LENGTH_LONG).show()
            return
        }

        //create object camp
        val newCamp = Camp(
            "",
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
            campType,
            "",
            true
        )

        // up user
        val user = Util.currentUser.value!!

        val key = mRef.child("camps").push().key!!
        newCamp.camp_id = key
        newCamp.image = link.toString()
        mRef.child("camps").child(newCamp.camp_id).setValue(newCamp)

        val hc = HaveCamp("", newCamp.camp_id, user.user_id, HaveCamp.OWNER)
        val key2 = mRef.child("have_camp").push().key!!
        hc.hc_id = key2
        Log.i("getValueOn", "have_camp ${key2}")
        mRef.child("have_camp").child(hc.hc_id).setValue(hc)
        Log.i("getValueOn", "have_camp ${hc.hc_id}")

        Toast.makeText(context!!, "ข้อมูลถูกเพิ่มแล้ว", Toast.LENGTH_LONG).show()

//        storeRef = FirebaseStorage.getInstance().reference
        val ref = storeRef.child("images").child("camps").child(newCamp.camp_id)
        ref.putFile(link).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener { uri ->
                mRef.child("camps").child(newCamp.camp_id).child("image").setValue(uri.toString())
                Toast.makeText(context!!, "เพิ่มข้อมูลค่ายสำเร็จ", Toast.LENGTH_LONG).show()
            }
        }
//            .addOnSuccessListener {

//                val err: Drawable =
//                    ContextCompat.getDrawable(context!!, R.drawable.ic_error_black_24dp)!!
//
//                Picasso.with(context!!).load(uri.toString())
//                    .transform(CropCircleTransformation()).error(err).into(img)
//        val item = link.toString()
//        Log.i("getImage", "success")
//
////                val user = Util.currentUser.value!!
//        Log.i("getImage", "id: ${user.user_id}")
//        mRef.child("camps").child(newCamp.camp_id).child("image").setValue(item)
    }
//    }
}
//}
//}


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


//}





