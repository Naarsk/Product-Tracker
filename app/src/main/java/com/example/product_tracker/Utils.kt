package com.example.product_tracker

import java.util.Calendar
class Utils {

    fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        return String.format("%02d:%02d:%02d", hour, minute, second)
    }
}