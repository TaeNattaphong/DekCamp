package com.example.dekcamp.data

data class Camp(
    var camp_id: String = "",
    var campname: String = "",
    var detail: String = "",
    var address: String = "",
    var contact: String = "",
    var price: Double = -1.0,
    var certificate: Boolean = false,
    var maxPeople: Int = -1,
    var minAge: Int = -1,
    var campStrat: Long = -1,     // day
    var campEnd: Long = -1,       // day
    var startRegis: Long = -1,    // day
    var endRegis: Long = -1,      // day
    var vote: Int = 0,
    var durationType: String = "",      // duration type
    var amountPeople: Int = 0,
    var payWhen: String = "",
    var campType: String = "",           // camp type
    var isAvailable: Boolean = true
)