package com.busschedule.register.util

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.convertTimePickerToUiTime(): String {
    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, this.hour)
    cal.set(Calendar.MINUTE, this.minute)
    cal.isLenient = false
//    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
//    return formatter.format(cal.time)
    return "${this.hour}:${this.minute}"
}