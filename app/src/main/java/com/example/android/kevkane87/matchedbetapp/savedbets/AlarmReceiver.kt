package com.example.android.kevkane87.matchedbetapp.savedbets

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.android.kevkane87.matchedbetapp.Constants
import com.example.android.kevkane87.matchedbetapp.MatchedBetDataItem

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.d(TAG, "broadcast receiver triggered")

        //val  = intent.getSerializableExtra("reminderId") as MatchedBetDataItem?

        //val remnderDataItem = intent.getSerializableExtra("reminderId")
        val bundle = intent.getBundleExtra(Constants.REMINDER_ID)
        val reminderDataItem = bundle?.getSerializable(Constants.REMINDER_ID) as MatchedBetDataItem


        Log.d(TAG, "received bundle" + reminderDataItem.toString())

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(context, reminderDataItem)
    }
}

private const val TAG = "Alarm receiver"