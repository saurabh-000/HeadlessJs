package com.headless

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate
import android.provider.Settings
import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext

class MainActivity : ReactActivity() {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  override fun getMainComponentName(): String = "Headless"

  /**
   * Returns the instance of the [ReactActivityDelegate]. We use [DefaultReactActivityDelegate]
   * which allows you to enable New Architecture with a single boolean flags [fabricEnabled]
   */
  override fun createReactActivityDelegate(): ReactActivityDelegate =
      DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)
    lateinit var reactContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("RNModule RequestCode",requestCode.toString())
        if (requestCode == 1001) {
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

            if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    alarmManager.canScheduleExactAlarms()
                } else {
                    TODO("VERSION.SDK_INT < S")

                }
            ) {
                // Permission granted, schedule the alarm and notify React Native
                scheduleExactAlarm()
            } else {
                // Permission denied, notify React Native
                Toast.makeText(this, "Please allow exact alarms to continue", Toast.LENGTH_LONG).show()
            }
        }
    }



    companion object{
        fun startService(context: Context,activity: MainActivity){
            activity.reactContext=context
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                // Check if the SCHEDULE_EXACT_ALARM permission is granted
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                if (!alarmManager.canScheduleExactAlarms()) {
                    // Prompt user to allow exact alarms
                    activity.requestExactAlarmPermission()
                } else {
                    activity.scheduleExactAlarm()
                }
            } else {
                // For Android versions below Android 12, directly schedule the alarm
                activity.scheduleExactAlarm()
            }
        }



        fun stopService(context: Context,activity: MainActivity){
            activity.cancelAlarm(context)
        }
    }



    private fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Redirect user to the system settings page to allow exact alarms

            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            startActivity(intent)
            Toast.makeText(this, "Please allow exact alarms to continue", Toast.LENGTH_LONG).show()
        }
    }

    private fun scheduleExactAlarm() {
        // Schedule the periodic alarm using AlarmManager
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Set a repeating alarm every 60 seconds (1 minute)
        val intervalMillis: Long = 15 * 1000  // 1 minute in milliseconds
        val triggerAtMillis: Long = SystemClock.elapsedRealtime() + intervalMillis

        // Set the exact alarm, depending on the Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println("here")
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerAtMillis,
                pendingIntent
            )
        } else {
            alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerAtMillis,
                intervalMillis,
                pendingIntent
            )
        }
        Log.d("MainActivity", "Initial alarm scheduled")
    }

    private fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager


        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0, // same request code used when scheduling the alarm
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Cancel the alarm
        alarmManager.cancel(pendingIntent)
        Log.d("RNModule","Cancel Alarm")
    }
}


