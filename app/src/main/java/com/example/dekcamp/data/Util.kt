package com.example.dekcamp.data

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
class Util {
    companion object {
        val DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy")
        val DATE_TIME_FORMAT = SimpleDateFormat("dd/MM/yyyy HH:mm")
        var currentUser = MutableLiveData<User>().also {
            it.value = User()
        }
        var lastSelected = ""
        val campType =
            arrayListOf(
                "อาสา",
                "อนุรักษ์",
                "พัฒนาเยาวชน",
                "ภาษา",
                "แนะแนว",
                "ติว",
                "เด็กมหาลัย"
            )
    }


}