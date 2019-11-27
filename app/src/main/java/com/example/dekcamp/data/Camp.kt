package com.example.dekcamp.data

data class Camp(
    var camp_id: String = "",
    var campname: String = "",
    var detail: String = "",
    var adress: String = "",
    var contact: String = "",
    var price: Int = 0,
    var certificate: String,
    var maxPeople: Int,
    var minAge: Int,
    var campStrat: String,
    var campEnd: String,
    var startRegis: String,
    var endRegis: String,
    var vote: Int,
    var typeCamp: String,
    var amountPeople: Int,
    var payWhen: String
)