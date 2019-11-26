package com.example.dekcamp.data

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import kotlin.math.abs

@SuppressLint("SimpleDateFormat")
class Util {
    companion object {
        val DATE_FORMAT = SimpleDateFormat("dd/MM/yyyy")
        val DATE_TIME_FORMAT = SimpleDateFormat("dd/MM/yyyy HH:mm")
        var currentUser = User()
    }
}