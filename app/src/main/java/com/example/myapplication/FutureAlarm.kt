package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class FutureAlarm : Fragment() {
    lateinit var view: ViewGroup
    lateinit var alarms: ArrayList<Alarm>
    lateinit var futureAlarms: ArrayList<Alarm>
    lateinit var dbHelper: DBHelper
    lateinit var future_alarm_list: RecyclerView
    lateinit var ListAdapter : AlarmAdatpter
    lateinit var mLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.future_alarm, container, false) as ViewGroup
        dbHelper = DBHelper.newInstance(activity!!)
        future_alarm_list = view.findViewById(R.id.future_alarms_list)
        alarms = dbHelper.getAlarms()
        futureAlarms =ArrayList<Alarm>()
        for (i in alarms.indices)
        {
            if (alarms.get(i).isEnabled)
                futureAlarms.add(alarms.get(i))
        }
        setupAlarms()
        return view
    }

    fun setupAlarms() {

        mLayoutManager = LinearLayoutManager(activity!!)
        future_alarm_list.layoutManager = mLayoutManager

        ListAdapter = AlarmAdatpter(activity!!, futureAlarms)
        future_alarm_list.adapter = ListAdapter

    }
}