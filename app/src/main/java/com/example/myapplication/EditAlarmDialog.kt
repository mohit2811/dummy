package com.example.myapplication

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.os.Build
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog

import kotlinx.android.synthetic.main.dialog_alarm.view.*

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
class EditAlarmDialog(val activity: Activity, val alarm: Alarm, val callback: () -> Unit) {
    private val view = activity.layoutInflater.inflate(R.layout.dialog_alarm, null)

    init {
        updateAlarmTime()

        view.apply {
            edit_alarm_time.setOnClickListener {
                TimePickerDialog(context, timeSetListener, alarm.timeInMinutes / 60, alarm.timeInMinutes % 60,true).show()
            }

            val dayLetters = activity.resources.getStringArray(R.array.week_day_letters).toList() as ArrayList<String>
            val dayIndexes = arrayListOf(0, 1, 2, 3, 4, 5, 6)


            dayIndexes.forEach {
                val pow = Math.pow(2.0, it.toDouble()).toInt()
                val day = activity.layoutInflater.inflate(R.layout.alarm_day, edit_alarm_days_holder, false) as TextView
                day.text = dayLetters[it]

                val isDayChecked = alarm.days and pow != 0
                edit_alarm_days_holder.addView(day)
            }
        }
    }

    private val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        alarm.timeInMinutes = hourOfDay * 60 + minute
        updateAlarmTime()
    }

    private fun updateAlarmTime() {
        view.edit_alarm_time.text =getFormattedTime(alarm.timeInMinutes * 60, false, true)
    }


    fun getFormattedTime(passedSeconds: Int, showSeconds: Boolean, makeAmPmSmaller: Boolean): SpannableString {
        val use24HourFormat = true
        val hours = (passedSeconds / 3600) % 24
        val minutes = (passedSeconds / 60) % 60
        val seconds = passedSeconds % 60

        return if (!use24HourFormat) {
            val formattedTime = formatTo12HourFormat(showSeconds, hours, minutes, seconds)
            val spannableTime = SpannableString(formattedTime)
            val amPmMultiplier = if (makeAmPmSmaller) 0.4f else 1f
            spannableTime.setSpan(RelativeSizeSpan(amPmMultiplier), spannableTime.length - 5, spannableTime.length, 0)
            spannableTime
        } else {
            val formattedTime = formatTime(showSeconds, use24HourFormat, hours, minutes, seconds)
            SpannableString(formattedTime)
        }
    }
    fun formatTo12HourFormat(showSeconds: Boolean, hours: Int, minutes: Int, seconds: Int): String {
        val appendable =if (hours >= 12) "pm" else "am"
        val newHours = if (hours == 0 || hours == 12) 12 else hours % 12
        return "${formatTime(showSeconds, false, newHours, minutes, seconds)} $appendable"
    }
    fun formatTime(showSeconds: Boolean, use24HourFormat: Boolean, hours: Int, minutes: Int, seconds: Int): String {
        val hoursFormat = if (use24HourFormat) "%02d" else "%01d"
        var format = "$hoursFormat:%02d"

        return if (showSeconds) {
            format += ":%02d"
            String.format(format, hours, minutes, seconds)
        } else {
            String.format(format, hours, minutes)
        }
    }

}
