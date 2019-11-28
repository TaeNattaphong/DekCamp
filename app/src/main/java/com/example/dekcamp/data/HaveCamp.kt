package com.example.dekcamp.data

class HaveCamp(
    var hc_id: String = "",
    var camp_id: String = "",
    var user_id: String = "",
    var relation: Int = -1
) {
    companion object {
        const val OWNER = 0
        const val PARTICIPANT = 1
    }
}