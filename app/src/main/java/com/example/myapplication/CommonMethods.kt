package com.example.myapplication

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.ViewPager

class CommonMethods {
    companion object{
        lateinit var edit_alarm_days_holder: LinearLayout
        lateinit var button_cancel: TextView
        lateinit var button_submit: TextView
        lateinit var title: TextView
        lateinit var edit_alarm_time: TextView
        val ALARM_SOUND_TYPE_ALARM = 1
        val ALARM_SOUND_TYPE_NOTIFICATION = 2
        fun getFormattedTime(
            passedSeconds: Int,
            showSeconds: Boolean,
            makeAmPmSmaller: Boolean
        ): SpannableString {
            val use24HourFormat = true
            val hours = (passedSeconds / 3600) % 24
            val minutes = (passedSeconds / 60) % 60
            val seconds = passedSeconds % 60

            return if (!use24HourFormat) {
                val formattedTime = formatTo12HourFormat(showSeconds, hours, minutes, seconds)
                val spannableTime = SpannableString(formattedTime)
                val amPmMultiplier = if (makeAmPmSmaller) 0.4f else 1f
                spannableTime.setSpan(
                    RelativeSizeSpan(amPmMultiplier),
                    spannableTime.length - 5,
                    spannableTime.length,
                    0
                )
                spannableTime
            } else {
                val formattedTime = formatTime(showSeconds, use24HourFormat, hours, minutes, seconds)
                SpannableString(formattedTime)
            }
        }

        fun formatTo12HourFormat(showSeconds: Boolean, hours: Int, minutes: Int, seconds: Int): String {
            val appendable = if (hours >= 12) "pm" else "am"
            val newHours = if (hours == 0 || hours == 12) 12 else hours % 12
            return "${formatTime(showSeconds, false, newHours, minutes, seconds)} $appendable"
        }

        fun formatTime(
            showSeconds: Boolean,
            use24HourFormat: Boolean,
            hours: Int,
            minutes: Int,
            seconds: Int
        ): String {
            val hoursFormat = if (use24HourFormat) "%02d" else "%01d"
            var format = "$hoursFormat:%02d"

            return if (showSeconds) {
                format += ":%02d"
                String.format(format, hours, minutes, seconds)
            } else {
                String.format(format, hours, minutes)
            }
        }

         fun getProperDayDrawable(
            selected: Boolean,
            alarmActivity: AlarmActivity
        ): Drawable {
            val drawableId =
                if (selected) R.drawable.circle_background_filled else R.drawable.circle_background_stroke
            val drawable =alarmActivity.resources.getDrawable(drawableId)
            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
            return drawable
        }


        fun createNewAlarm(
            timeInMinutes: Int,
            weekDays: Int,
            alarmActivity: Activity
        ): Alarm {
            val defaultAlarmSound = getDefaultAlarmSound(alarmActivity, ALARM_SOUND_TYPE_ALARM)
            return Alarm(
                0,
                timeInMinutes,
                weekDays,
                false,
                false,
                defaultAlarmSound.title,
                defaultAlarmSound.uri,
                ""
            )
        }

        fun getDefaultAlarmSound(type1: Activity, type: Int) =
            AlarmSound(0, getDefaultAlarmTitle(type,type1), getDefaultAlarmUri(type,type1).toString())

        fun getDefaultAlarmUri(type: Int, type1: Activity) =
            RingtoneManager.getDefaultUri(if (type == ALARM_SOUND_TYPE_NOTIFICATION) RingtoneManager.TYPE_NOTIFICATION else RingtoneManager.TYPE_ALARM)

        fun getDefaultAlarmTitle(type: Int, type1: Activity) =
            RingtoneManager.getRingtone(type1, getDefaultAlarmUri(type, type1))?.getTitle(type1)
                ?: "alarm"

        fun Int.removeBit(bit: Int) = addBit(bit) - bit

        fun Int.addBit(bit: Int) = this or bit



    }
}