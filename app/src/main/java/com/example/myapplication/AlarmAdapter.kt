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
import com.example.myapplication.CommonMethods.Companion.getFormattedTime


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
        holder.time.text = CommonMethods.getFormattedTime(alarmlist.get(position).timeInMinutes * 60, false, true)


        holder.text_day.text = getSelectedDaysString(alarmlist.get(position).days)


        holder.text_title.text = alarmlist.get(position).label
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


    fun getSelectedDaysString(bitMask: Int): String {
        val dayBits = arrayListOf(MONDAY_BIT, TUESDAY_BIT, WEDNESDAY_BIT, THURSDAY_BIT, FRIDAY_BIT, SATURDAY_BIT, SUNDAY_BIT)
        val weekDays = mContext.resources.getStringArray(R.array.week_day).toList() as java.util.ArrayList<String>

        var days = ""
        dayBits.forEachIndexed { index, bit ->
            if (bitMask and bit != 0) {
                days += "${weekDays[index]}, "
            }
        }
        return days.trim().trimEnd(',')
    }
}