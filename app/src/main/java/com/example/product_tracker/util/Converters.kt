package com.example.product_tracker.util

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    @TypeConverter
    fun timestampToDate(time : Long): Date {
        return Date(time) }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}