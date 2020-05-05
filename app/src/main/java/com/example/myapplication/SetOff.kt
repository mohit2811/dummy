package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.IntegerRes
import kotlinx.android.synthetic.main.activity_set_off.*
import java.util.ArrayList
import kotlin.properties.Delegates

class SetOff : AppCompatActivity() {
var idd:Int = 0
    private lateinit var alarms : Alarm
  var   dbHelper = DBHelper.newInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_off)
        idd =intent.getIntExtra("id",0)
        alarms = dbHelper.getAlarmWithId(idd)!!
        title_off.text=alarms.label
        alarm_time_off.text="Time "+ CommonMethods.getFormattedTime(
            alarms.timeInMinutes * 60,
            false,
            true
        ).toString()


    }

    fun setOffId(view: View) {
        if (dbHelper.updateAlarmEnabledState(idd, false)) {
            startActivity(Intent(this, AlarmActivity::class.java))
            finish()
        }
    }
}
