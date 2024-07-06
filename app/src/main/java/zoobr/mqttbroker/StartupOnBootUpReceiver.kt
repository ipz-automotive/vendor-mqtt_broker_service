package zoobr.mqttbroker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import zoobr.mqttbroker.service.MqttService

class startupOnBootUpReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.d("BOOTUPEX", "STARTING")

        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            val activityIntent = Intent(context, MqttService::class.java)
            Log.d("BOOTUPEX", "STARY WSTAL")
            context.startService(activityIntent)
        }
    }
}