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
    private var alarms = ArrayList<Alarm>()
  var   dbHelper = DBHelper.newInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_off)
        title_off.text=intent.getStringExtra("title")
        alarm_time_off.text="Time "+intent.getStringExtra("time")
         idd =intent.getIntExtra("id",0)
        alarms =dbHelper.getAlarms()

    }

    fun setOffId(view: View) {
        if (dbHelper.updateAlarmEnabledState(idd, false)) {
            val alarm = alarms.firstOrNull { it.id == idd } ?: return
            alarm.isEnabled = false
            startActivity(Intent(this, AlarmActivity::class.java))
            finish()
        } else {

        }
    }
}
