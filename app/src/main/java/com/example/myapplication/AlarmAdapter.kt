package com.example.myapplication


import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.with


class AlarmAdatpter(var mContext: Activity,
                    var alarmlist: ArrayList<Alarm>) : RecyclerView.Adapter<AlarmAdatpter.ViewHolder>() {
     val MONDAY_BIT = 1
     val TUESDAY_BIT = 2
     val WEDNESDAY_BIT = 4
     val THURSDAY_BIT = 8
     val FRIDAY_BIT = 16
     val SATURDAY_BIT = 32
     val SUNDAY_BIT = 64

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.time.text = getFormattedTime(alarmlist.get(position).timeInMinutes * 60, false, true)


        holder.text_day.text = getSelectedDaysString(alarmlist.get(position).days)


        holder.text_title.text = alarmlist.get(position).label

holder.alarm_holder.setOnClickListener {
    val builder = AlertDialog.Builder(mContext)
    builder.setTitle("Set List Type")

    builder.setMessage("Are you sure you want to set this alarm as past?")

    builder.setPositiveButton("YES"){dialog, which ->
  var i =Intent(mContext, SetOff::class.java)
        i.putExtra("title",alarmlist.get(position).label)
        i.putExtra("time", getFormattedTime(alarmlist.get(position).timeInMinutes * 60, false, true).toString())
        i.putExtra("id",alarmlist.get(position).id)
        mContext.startActivity(i)
        dialog.dismiss()
    }
    builder.setNegativeButton("No"){dialog,which ->
        dialog.dismiss()
    }
    val dialog: AlertDialog = builder.create()
    dialog.show()
}

    }


    override fun getItemCount(): Int {
        return alarmlist!!.size
    }

    open class ViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        lateinit var time: TextView
        lateinit var text_day: TextView
        lateinit var text_title: TextView
        lateinit var alarm_holder: RelativeLayout

        init {
            if (view != null) {
                time = view.findViewById(R.id.alarm_time)
                text_day = view.findViewById(R.id.alarm_days)
                text_title = view.findViewById(R.id.alarm_label)
                alarm_holder = view.findViewById(R.id.alarm_holder)
            }
        }
    }

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
    fun getSelectedDaysString(bitMask: Int): String {
        val dayBits = arrayListOf(MONDAY_BIT, TUESDAY_BIT, WEDNESDAY_BIT, THURSDAY_BIT, FRIDAY_BIT, SATURDAY_BIT, SUNDAY_BIT)
        val weekDays = mContext.resources.getStringArray(R.array.week_day_letters).toList() as java.util.ArrayList<String>

        var days = ""
        dayBits.forEachIndexed { index, bit ->
            if (bitMask and bit != 0) {
                days += "${weekDays[index]}, "
            }
        }
        return days.trim().trimEnd(',')
    }
}