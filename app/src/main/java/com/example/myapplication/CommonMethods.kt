package com.example.myapplication

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.widget.Toast
import androidx.core.app.AlarmManagerCompat
import java.util.*
import kotlin.math.pow

class CommonMethods {
    companion object {
        val HOUR_MINUTES = 60
        val DAY_MINUTES = 24 * HOUR_MINUTES
        val WEEK_MINUTES = DAY_MINUTES * 7
        val MONTH_MINUTES = DAY_MINUTES * 30
        val YEAR_MINUTES = DAY_MINUTES * 365
        val MINUTE_SECONDS = 60
        val HOUR_SECONDS = HOUR_MINUTES * 60
        val DAY_SECONDS = DAY_MINUTES * 60
        val WEEK_SECONDS = WEEK_MINUTES * 60
        val MONTH_SECONDS = MONTH_MINUTES * 60
        val YEAR_SECONDS = YEAR_MINUTES * 60
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
                val formattedTime =
                    formatTime(showSeconds, use24HourFormat, hours, minutes, seconds)
                SpannableString(formattedTime)
            }
        }

        fun formatTo12HourFormat(
            showSeconds: Boolean,
            hours: Int,
            minutes: Int,
            seconds: Int
        ): String {
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
            val drawable = alarmActivity.resources.getDrawable(drawableId)
            drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
            return drawable
        }


        fun createNewAlarm(
            timeInMinutes: Int,
            weekDays: Int,
            alarmActivity: Activity
        ): Alarm {
            return Alarm(
                0,
                timeInMinutes,
                weekDays,
                false,
                "Alarm"
            )
        }

        fun Int.removeBit(bit: Int) = addBit(bit) - bit

        fun Int.addBit(bit: Int) = this or bit

        fun scheduleNextAlarm(
            alarm: Alarm,
            showToast: Boolean,
            alarmActivity: Activity
        ) {
            val calendar = Calendar.getInstance()
            calendar.firstDayOfWeek = Calendar.MONDAY
            for (i in 0..7) {
                val currentDay = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7
                val isCorrectDay = alarm.days and 2.0.pow(currentDay).toInt() != 0
                val currentTimeInMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(
                    Calendar.MINUTE
                )
                if (isCorrectDay && (alarm.timeInMinutes > currentTimeInMinutes || i > 0)) {
                    val triggerInMinutes =
                        alarm.timeInMinutes - currentTimeInMinutes + (i * DAY_MINUTES)
                    setupAlarmClock(
                        alarm,
                        triggerInMinutes * 60 - calendar.get(Calendar.SECOND),
                        alarmActivity
                    )

                    if (showToast) {
                        showRemainingTimeMessage(triggerInMinutes, alarmActivity)
                    }
                    break
                } else {
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                }
            }
        }

        fun showRemainingTimeMessage(totalMinutes: Int, alarmActivity: Activity) {
            val fullString =
                "Alarm Goes Off in " + formatMinutesToTimeString(totalMinutes, alarmActivity)
            Toast.makeText(alarmActivity, fullString, Toast.LENGTH_LONG).show()
        }

        fun formatMinutesToTimeString(totalMinutes: Int, alarmActivity: Activity) =
            formatSecondsToTimeString(totalMinutes * 60, alarmActivity)

        fun formatSecondsToTimeString(totalSeconds: Int, alarmActivity: Activity): String {
            val days = totalSeconds / DAY_SECONDS
            val hours = (totalSeconds % DAY_SECONDS) / HOUR_SECONDS
            val minutes = (totalSeconds % HOUR_SECONDS) / MINUTE_SECONDS
            val seconds = totalSeconds % MINUTE_SECONDS
            val timesString = StringBuilder()
            if (days > 0) {
                val daysString = "$days Days"
                timesString.append("$daysString, ")
            }

            if (hours > 0) {
                val hoursString = "$hours hours"
                timesString.append("$hoursString, ")
            }

            if (minutes > 0) {
                val minutesString = "$minutes minutes"
                timesString.append("$minutesString, ")
            }

            if (seconds > 0) {
                val secondsString = "$seconds seconds"
                timesString.append(secondsString)
            }

            var result = timesString.toString().trim().trimEnd(',')
            if (result.isEmpty()) {
                result = String.format(alarmActivity.resources.getQuantityString(minutes, 0, 0))
            }
            return result
        }

        fun setupAlarmClock(
            alarm: Alarm,
            triggerInSeconds: Int,
            alarmActivity: Activity
        ) {
            val alarmManager = alarmActivity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val targetMS = System.currentTimeMillis() + triggerInSeconds * 1000
            AlarmManagerCompat.setAlarmClock(
                alarmManager,
                targetMS,
                getOpenAlarmTabIntent(alarmActivity),
                getAlarmIntent(alarm, alarmActivity)
            )
        }

        fun getOpenAlarmTabIntent(alarmActivity: Activity): PendingIntent {
            val intent = Intent(alarmActivity, AlarmActivity::class.java)
            return PendingIntent.getActivity(
                alarmActivity,
                9996,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }


        fun getAlarmIntent(
            alarm: Alarm,
            alarmActivity: Activity
        ): PendingIntent {
            val intent = Intent(alarmActivity, AlarmReceiver::class.java)
            intent.putExtra("id", alarm.id)
            return PendingIntent.getBroadcast(
                alarmActivity,
                alarm.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }
}