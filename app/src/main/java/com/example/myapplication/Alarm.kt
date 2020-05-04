package com.example.myapplication

data class Alarm(val id: Int, var timeInMinutes: Int, var days: Int, var isEnabled: Boolean, var vibrate: Boolean, var soundTitle: String,
                 var soundUri: String, var label: String)
data class AlarmSound(val id: Int, var title: String, var uri: String)