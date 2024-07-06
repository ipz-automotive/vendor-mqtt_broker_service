package zoobr.mqttbroker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import zoobr.mqttbroker.databinding.SettingsActivityBinding
import zoobr.mqttbroker.service.MqttService
import zoobr.mqttbroker.utils.AppPreferences
import zoobr.mqttbroker.utils.Utils.getIPAddress

class MainActivity : AppCompatActivity() {

    private var TAG = MainActivity::class.java.simpleName

    private var _binding: SettingsActivityBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (AppPreferences.firstRun) {
            initSharedPrefs()
            Log.d(TAG, "onCreate: its first time run")
        }
        setIP()
        val serviceIntent = Intent(this, MqttService::class.java)
        startForegroundService(serviceIntent)
    }

    override fun onResume() {
        super.onResume()
        checkForInAppUpdate()
    }

    private fun checkForInAppUpdate() {
        val updateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = updateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && it.isUpdateTypeAllowed(
                    AppUpdateType.IMMEDIATE
                )
            ) {
                updateManager.startUpdateFlowForResult(
                    it,
                    this,
                    AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
                    0
                )
            }
        }
    }

    private fun initSharedPrefs() {
        AppPreferences.mqttHost = getIPAddress(true)
        AppPreferences.mqttPort = "1883"
        AppPreferences.mqttAuthStatus = false
        AppPreferences.mqttUserName = null
        AppPreferences.mqttPassword = null
        AppPreferences.firstRun = false
    }

    private fun setIP() {
        AppPreferences.mqttHost = getIPAddress(true)
    }
}



