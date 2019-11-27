package com.example.dekcamp.data

data class Camp(
    var campname: String = "",
    var detail: String = "",
    var adress: String = "",
    var contact: String = "",
    var price: Int = 0,
    var certificate: Boolean,
    var maxPeople: Int,
    var minAge: Int,
    var campStrat: String,
    var campEnd: String,
    var startRegis: String,
    var endRegis: String,
    var vote: Int,
    var typeCamp: Boolean,
    var amountPeople: Int
)