package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.media.RingtoneManager
import com.example.myapplication.CommonMethods.Companion.ALARM_SOUND_TYPE_ALARM
import com.example.myapplication.CommonMethods.Companion.ALARM_SOUND_TYPE_NOTIFICATION
import java.util.*

class DBHelper private constructor(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private val ALARMS_TABLE_NAME = "Alarms"
    private val COL_ID = "id"
    private val COL_TIME_IN_MINUTES = "time_in_minutes"
    private val COL_DAYS = "days"
    private val COL_IS_ENABLED = "is_enabled"
    private val COL_LABEL = "label"
     val MONDAY_BIT = 1
     val TUESDAY_BIT = 2
     val WEDNESDAY_BIT = 4
     val THURSDAY_BIT = 8

    val FRIDAY_BIT = 16
     val SATURDAY_BIT = 32
     val SUNDAY_BIT = 64
    fun Cursor.getStringValue(key: String) = getString(getColumnIndex(key))

    fun Cursor.getIntValue(key: String) = getInt(getColumnIndex(key))

    fun Cursor.getLongValue(key: String) = getLong(getColumnIndex(key))

    fun Cursor.getBlobValue(key: String) = getBlob(getColumnIndex(key))

    private val mDb = writableDatabase

    companion object {
        private const val DB_VERSION = 1
        const val DB_NAME = "alarms.db"
        var dbInstance: DBHelper? = null

        fun newInstance(context: Context): DBHelper {
            if (dbInstance == null)
                dbInstance = DBHelper(context)

            return dbInstance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $ALARMS_TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_TIME_IN_MINUTES INTEGER, $COL_DAYS INTEGER, " +
                "$COL_IS_ENABLED INTEGER,  $COL_LABEL TEXT)")
        insertInitialAlarms(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    private fun insertInitialAlarms(db: SQLiteDatabase) {
        val weekDays = MONDAY_BIT or TUESDAY_BIT or WEDNESDAY_BIT or THURSDAY_BIT or FRIDAY_BIT
        val weekDaysAlarm = createNewAlarm(420, weekDays)
        insertAlarm(weekDaysAlarm, db)

        val weekEnd = SATURDAY_BIT or SUNDAY_BIT
        val weekEndAlarm = createNewAlarm(540, weekEnd)
        insertAlarm(weekEndAlarm, db)
    }

    fun insertAlarm(alarm: Alarm, db: SQLiteDatabase = mDb): Boolean {
        val values = fillAlarmContentValues(alarm)
        return db.insert(ALARMS_TABLE_NAME, null, values) != -1L
    }



    private fun fillAlarmContentValues(alarm: Alarm): ContentValues {
        return ContentValues().apply {
            put(COL_TIME_IN_MINUTES, alarm.timeInMinutes)
            put(COL_DAYS, alarm.days)
            put(COL_IS_ENABLED, alarm.isEnabled)
            put(COL_LABEL, alarm.label)
        }
    }
    fun updateAlarm(alarm: Alarm): Boolean {
        val selectionArgs = arrayOf(alarm.id.toString())
        val values = fillAlarmContentValues(alarm)
        val selection = "$COL_ID = ?"
        return mDb.update(ALARMS_TABLE_NAME, values, selection, selectionArgs) == 1
    }
    fun getEnabledAlarms() = getAlarms().filter { it.isEnabled }
    fun getAlarmWithId(id: Int) = getAlarms().firstOrNull { it.id == id }
    fun getAlarmlast(): Alarm {
        val db = this.readableDatabase
        val array_list = ArrayList<Alarm>()
        var alarm:Alarm
        val res = db.rawQuery(
            "SELECT * FROM "+ALARMS_TABLE_NAME+" ORDER BY id DESC LIMIT 1;",
            null
        )
        res!!.moveToLast()

                val id = res.getIntValue(COL_ID)
                val timeInMinutes = res.getIntValue(COL_TIME_IN_MINUTES)
                val days = res.getIntValue(COL_DAYS)
                val isEnabled = res.getIntValue(COL_IS_ENABLED) == 1

                val label = res.getStringValue(COL_LABEL)

                 alarm = Alarm(id, timeInMinutes, days, isEnabled,  label)

        return alarm
    }
    fun getAlarms(): ArrayList<Alarm> {
        val alarms = ArrayList<Alarm>()
        val cols = arrayOf(COL_ID, COL_TIME_IN_MINUTES, COL_DAYS, COL_IS_ENABLED, COL_LABEL)
        var cursor: Cursor? = null
        try {
            cursor = mDb.query(ALARMS_TABLE_NAME, cols, null, null, null, null, null)
            if (cursor?.moveToFirst() == true) {
                do {
                    try {
                        val id = cursor.getIntValue(COL_ID)
                        val timeInMinutes = cursor.getIntValue(COL_TIME_IN_MINUTES)
                        val days = cursor.getIntValue(COL_DAYS)
                        val isEnabled = cursor.getIntValue(COL_IS_ENABLED) == 1
                        val label = cursor.getStringValue(COL_LABEL)
                        val alarm = Alarm(id, timeInMinutes, days, isEnabled,  label)
                        alarms.add(alarm)
                    } catch (e: Exception) {
                        continue
                    }
                } while (cursor.moveToNext())
            }
        } finally {
            cursor?.close()
        }

        return alarms
    }

    fun updateAlarmEnabledState(id: Int, isEnabled: Boolean): Boolean {
        val selectionArgs = arrayOf(id.toString())
        val values = ContentValues()
        values.put(COL_IS_ENABLED, isEnabled)
        val selection = "$COL_ID = ?"
        return mDb.update(ALARMS_TABLE_NAME, values, selection, selectionArgs) == 1
    }

    fun createNewAlarm(timeInMinutes: Int, weekDays: Int): Alarm {
        return Alarm(0, timeInMinutes, weekDays, false,  "Alarm")
    }

}
