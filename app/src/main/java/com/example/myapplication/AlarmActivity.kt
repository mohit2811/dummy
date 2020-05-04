package com.example.myapplication

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.chaufferapplication.adapters.ViewPagerAdapter
import com.example.myapplication.CommonMethods.Companion.addBit
import com.example.myapplication.CommonMethods.Companion.createNewAlarm
import com.example.myapplication.CommonMethods.Companion.removeBit
import kotlinx.android.synthetic.main.activity_alarm.*

class AlarmActivity : AppCompatActivity() {
    lateinit var dbHelper: DBHelper
    val DEFAULT_ALARM_MINUTES = 480
    val alarm = createNewAlarm(DEFAULT_ALARM_MINUTES, 0, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        initTabs()

        dbHelper = DBHelper.newInstance(applicationContext)
        alarm.isEnabled = true

    }

    private fun initTabs() {

        layout_tabs.addTab(layout_tabs.newTab().setText("Past"))
        layout_tabs.addTab(layout_tabs.newTab().setText("Future"))
        layout_tabs.setTabTextColors(
            Color.parseColor("#999999"),
            Color.parseColor("#2977C9")
        )
        setTabAdapter()
    }

    private fun setTabAdapter() {

        var viewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager, layout_tabs.tabCount)
        pager.setAdapter(viewPagerAdapter)
        layout_tabs.setupWithViewPager(pager)
        pager.setOffscreenPageLimit(0)
        pager.setCurrentItem(1)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun setAlarm(view: View) {
        val mDialog =
            Dialog(this)
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setCanceledOnTouchOutside(false)
        mDialog.setCancelable(false)
        mDialog.setContentView(R.layout.dialog_alarm)
        CommonMethods.button_cancel = mDialog.findViewById(R.id.button)
        CommonMethods.button_submit = mDialog.findViewById(R.id.button2)
        CommonMethods.title = mDialog.findViewById<EditText>(R.id.edit_alarm_label)
        CommonMethods.edit_alarm_time = mDialog.findViewById(R.id.edit_alarm_time)
        CommonMethods.edit_alarm_days_holder = mDialog.findViewById(R.id.edit_alarm_days_holder)
        updateAlarmTime()
        CommonMethods.edit_alarm_time.setOnClickListener {
            TimePickerDialog(
                this,
                timeSetListener,
                alarm.timeInMinutes / 60,
                alarm.timeInMinutes % 60,
                true
            ).show()
        }
        CommonMethods.button_cancel.setOnClickListener {
            mDialog.dismiss()
        }
        CommonMethods.button_submit.setOnClickListener {
            if (alarm.days == 0) {
                return@setOnClickListener
            }

            alarm.label = CommonMethods.title.text.toString()

            if (alarm.id == 0) {
                if (dbHelper.insertAlarm(alarm)) {

                    startActivity(Intent(this, AlarmActivity::class.java))
                    finish()
                    mDialog.dismiss()
                }
            } else {
                if (dbHelper.updateAlarm(alarm)) {

                }
            }
        }
        val dayLetters =
            resources.getStringArray(R.array.week_day_letters).toList() as ArrayList<String>
        val dayIndexes = arrayListOf(0, 1, 2, 3, 4, 5, 6)


        dayIndexes.forEach {
            val pow = Math.pow(2.0, it.toDouble()).toInt()
            val day = layoutInflater.inflate(
                R.layout.alarm_day,
                CommonMethods.edit_alarm_days_holder,
                false
            ) as TextView
            day.text = dayLetters[it]

            val isDayChecked = alarm.days and pow != 0
            day.background = CommonMethods.getProperDayDrawable(isDayChecked, this)


            day.setOnClickListener {
                val selectDay = alarm.days and pow == 0
                if (selectDay) {
                    alarm.days = alarm.days.addBit(pow)
                } else {
                    alarm.days = alarm.days.removeBit(pow)
                }
                day.background = CommonMethods.getProperDayDrawable(selectDay, this)
            }

            CommonMethods.edit_alarm_days_holder.addView(day)
            mDialog.show()

        }


    }

    private val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        alarm.timeInMinutes = hourOfDay * 60 + minute
        updateAlarmTime()
    }

    private fun updateAlarmTime() {
        CommonMethods.edit_alarm_time.text =
            CommonMethods.getFormattedTime(alarm.timeInMinutes * 60, false, true)
    }


}
