package com.headless;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

class RNModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String {
        return "RNModule"
    }




    @ReactMethod fun startService() {
        Log.d("RNModule", "Service started");
        val mainActivityInstance = currentActivity as MainActivity? // Get current activity instance
        mainActivityInstance?.let {
            MainActivity.startService(this.reactApplicationContext, it)
        }
    }

    @ReactMethod fun stopService() {
        Log.d("RNModule", "Service stopped");
        val mainActivityInstance = currentActivity as MainActivity? // Get current activity instance
        mainActivityInstance?.let {
            MainActivity.stopService(this.reactApplicationContext, it)
        }

    }




}
//public class RNModule extends ReactContextBaseJavaModule {
//    RNModule(ReactApplicationContext context) {
//        super(context);
//    }
//    @NonNull
//    @Override
//    public String getName() {
//        return "RNModule";
//    }
//
//    @ReactMethod
//    public void startService() {
//        Log.d("RNModule", "Service started");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            // Check if the SCHEDULE_EXACT_ALARM permission is granted
//            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//            if (!alarmManager.canScheduleExactAlarms()) {
//                // Prompt user to allow exact alarms
//                requestExactAlarmPermission()
//            } else {
//                scheduleExactAlarm()
//            }
//        } else {
//            // For Android versions below Android 12, directly schedule the alarm
//            scheduleExactAlarm()
//        }
//    }
//
//    @ReactMethod
//    public void stopService() {
//        Log.d("RNModule", "Stop service");
//    }
//
//    private fun requestExactAlarmPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            // Redirect user to the system settings page to allow exact alarms
//
//            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
//            startActivity(intent)
//            Toast.makeText(this, "Please allow exact alarms to continue", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private fun scheduleExactAlarm() {
//        // Schedule the periodic alarm using AlarmManager
//        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, AlarmReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//
//        // Set a repeating alarm every 60 seconds (1 minute)
//        val intervalMillis: Long = 15 * 1000  // 1 minute in milliseconds
//        val triggerAtMillis: Long = SystemClock.elapsedRealtime() + intervalMillis
//
//        // Set the exact alarm, depending on the Android version
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            System.out.println("here")
//            alarmManager.setExactAndAllowWhileIdle(
//                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                    triggerAtMillis,
//                    pendingIntent
//            )
//        } else {
//            alarmManager.setRepeating(
//                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                    triggerAtMillis,
//                    intervalMillis,
//                    pendingIntent
//            )
//        }
//        Log.d("MainActivity", "Initial alarm scheduled")
//    }
//
//
//}
