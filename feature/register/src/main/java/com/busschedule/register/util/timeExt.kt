package com.busschedule.register.entity

import androidx.compose.material3.ExperimentalMaterial3Api
import com.busschedule.domain.model.response.schedule.Time
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Time.toHourAndMinute() =
    "${this.hour}:${this.minute}"


@OptIn(ExperimentalMaterial3Api::class)
fun Time.convertTimePickerToUiTime(): String {
    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, this.hour)
    cal.set(Calendar.MINUTE, this.minute)
    cal.isLenient = false
    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return formatter.format(cal.time)
}