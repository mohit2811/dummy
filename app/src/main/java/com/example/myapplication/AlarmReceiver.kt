package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.PowerManager
import androidx.core.content.ContextCompat.getSystemService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra("id", -1)
        val dbHelper = DBHelper.newInstance(context)
        val alarm = dbHelper.getAlarmWithId(id) ?: return

            Intent(context, SetOff::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra("id", id)
                context.startActivity(this)
            }
        }

    }


