package com.example.dekcamp.data

data class User (
    var firstname: String = "",
    var lastname: String = "",
    val nickname: String = "",
    var student_id: String = "",
    var gender: String = "",
    var birthday: Long = -1,
    var email: String = "",
    var phone: String = "",
    var allegic: String = "",
    var user_id: String = ""
)