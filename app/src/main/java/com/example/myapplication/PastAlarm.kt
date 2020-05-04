package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class PastAlarm : Fragment() {
    lateinit var view: ViewGroup
    lateinit var alarms: ArrayList<Alarm>
    lateinit var pastAlarms: ArrayList<Alarm>
    lateinit var dbHelper: DBHelper
    lateinit var past_alarm_list: RecyclerView
    lateinit var ListAdapter : AlarmAdatpter
    lateinit var mLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.past_alarm, container, false) as ViewGroup
        dbHelper = DBHelper.newInstance(activity!!)
        past_alarm_list = view.findViewById(R.id.past_alarms_list)
        pastAlarms =ArrayList<Alarm>()
        alarms = dbHelper.getAlarms()
        for (i in alarms.indices)
        {
            if (!alarms.get(i).isEnabled)
                pastAlarms.add(alarms.get(i))
        }
        setupAlarms()
        return view
    }

    fun setupAlarms() {

        mLayoutManager = LinearLayoutManager(activity!!)
        past_alarm_list.layoutManager = mLayoutManager

        ListAdapter = AlarmAdatpter(activity!!, pastAlarms)
        past_alarm_list.adapter = ListAdapter

    }
}